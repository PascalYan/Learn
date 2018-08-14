package com.pascalx.view.service.Impl;

import com.pascalx.view.rpc.PaymentRpcService;
import com.pascalx.view.rpc.PaymentRpcServiceImpl;

import java.util.*;
import java.util.concurrent.*;

/**
 * future 的方式实现，timer控制总超时时间
 *
 * @author yanghui
 * @date 2018/8/14.
 */
public class PaymentServiceFutureImpl extends AbstractPaymentService {


    /**
     * 为了快速响应，优先用线程去执行(不往任务队列去放)
     * 直到线程数达到corePoolSize(==maximumPoolSize)，即达到系统处理瓶颈，则放入任务队列
     * 流量较小时，线程空闲时间达到1s，则空闲线程被回收
     * corePoolSize，注意到rpc调用主要是IO型操作，cpu由于io阻塞大部分空闲，所以结合机器资源限制，配置尽可能大(系统极限)，也即配置创建固定大小的线程池(和maximumPoolSize一样)
     * 由于创建一个线程，默认需要1M的栈空间，结合机器内存的情况以及jvm堆内存大小以及性能测试情况来设置corePoolSize
     * 这里先假设是corePoolSize=1000
     */
    private ExecutorService pool = new ThreadPoolExecutor(1000, 1000,
            1000L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    private PaymentRpcService paymentRpcService = new PaymentRpcServiceImpl();

    /**
     * 用于作超时控制，中断主线程，方法返回
     */
    private Timer timer = new Timer();

    /**
     * @param paymentTypes 支付类型
     * @param timeout      方法超时时间
     * @return 可用的支付类型列表, 如果无可用支付券列表或者入参支付类型为空，返回空list
     */
    @Override
    public List<String> enablePayments(List<String> paymentTypes, long timeout) {
//        利用set，避免timer超时后的结果处理与主线程的结果处理重复（比上锁同步的方式效率高）
        Set<String> enablePaymentsSet = Collections.synchronizedSet(new HashSet<>());

        if (paymentTypes == null || paymentTypes.size() == 0) {
            return new ArrayList<>(0);
        } else {
            Map<String, Future<Boolean>> futures = new HashMap<>(paymentTypes.size());

//            超时控制
            timeout(timeout, futures, Thread.currentThread(), enablePaymentsSet);

//            提交线程池并发调用rpc
            for (String paymentType : paymentTypes) {
                futures.put(paymentType, pool.submit(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return paymentRpcService.isEnabled(paymentType);
                    }
                }));
            }

//          future集处理
            for (Map.Entry<String, Future<Boolean>> entry : futures.entrySet()) {
                try {
                    if (entry.getValue().get()) {
                        enablePaymentsSet.add(entry.getKey());
                    }
//               以下异常，不需要处理
//               正常的线程中断
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                } catch (ExecutionException e) {
                    System.out.println(e.getMessage());
//                此处和timer发生并发，有可能任务被timer取消了，get()才要执行，将抛出CancellationException
                } catch (CancellationException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
/*        线程池任务队列已满，且达到最大线程数，采用默认的拒绝策略，可能会报默认的RejectedExecutionException等，以及rpc调用网络异常等
 *         此处不需要处理异常，打印日志，直接返回正常可用的支付列表
 *         如果对方系统不稳定，可以考虑熔断策略
 */
                }
            }

            return toList(enablePaymentsSet);
        }
    }


    public void timeout(long timeout, Map<String, Future<Boolean>> futures, Thread threadToInterrupt, Set<String> enablePaymentsSet) {

//      利用timer进行超时控制，timer 判断超时，中断主线程，再一次处理结果，把未执行完成的任务取消
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("超时，取消任务直接返回...");
//                超时，方法返回前，再次遍历处理future集，避免主线程未处理完被中断
                if (futures.size() > 0) {
                    for (Map.Entry<String, Future<Boolean>> entry : futures.entrySet()) {
                        try {
                            if (entry.getValue().isDone()) {
//                              可用的支付方式
                                if (entry.getValue().get()) {
                                    enablePaymentsSet.add(entry.getKey());
                                }
//                            超时未完成的，取消
                            } else {
                                entry.getValue().cancel(true);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
//                        中断主线程，方法返回
                    threadToInterrupt.interrupt();
                }
            }
        }, timeout);
    }


    private List<String> toList(Set<String> stringSet) {
        List<String> result = new ArrayList<>(stringSet.size());
        for (String s : stringSet) {
            result.add(s);
        }
        return result;
    }

}


