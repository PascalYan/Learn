package com.pascalx.view;

import com.pascalx.view.rpc.PaymentRemoteSerivce;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author yanghui10
 * @date 2018/8/14.
 */
public class ThreadPoolImpl extends AbstractPaymentService {

    private ExecutorService pool = Executors.newFixedThreadPool(1000);

    private PaymentRemoteSerivce paymentRemoteSerivce = new PaymentRemoteSerivce();

    public static long timeout = 50;
    public static long totalTimeout = 300;


    /**
     * @param paymentTypes 支付类型
     * @return 可用的支付类型列表, 如果无可用支付券列表或者入参支付类型为空，返回空list
     */
    @Override
    public List<String> enablePayments(List<String> paymentTypes) {
        List<String> enablePayments = new ArrayList<>();
        if (paymentTypes != null && paymentTypes.size() > 0) {
            Map<String, Future<Boolean>> futures = new HashMap<>(paymentTypes.size());
            for (String paymentType : paymentTypes) {
                futures.put(paymentType, pool.submit(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return paymentRemoteSerivce.isEnabled(paymentType).getIsEnable();
                    }
                }));
            }

            long currentTimeOut = timeout;

            Iterator<Map.Entry<String, Future<Boolean>>> iterator = futures.entrySet().iterator();
            try {
                Map.Entry<String, Future<Boolean>> entry;
                while (iterator.hasNext()) {
                    if ((entry = iterator.next()).getValue().get(currentTimeOut, TimeUnit.MILLISECONDS)) {
                        enablePayments.add(entry.getKey());
                        iterator.remove();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
//           返回前，再次遍历
            if (futures.size() > 0) {
                for (Map.Entry<String, Future<Boolean>> entry : futures.entrySet()) {
                    try {
                        if (entry.getValue().isDone()&&entry.getValue().get()) {
                            enablePayments.add(entry.getKey());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return enablePayments;
    }


}
