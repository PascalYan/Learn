package com.pascalx.view.alipay.service.Impl;

import com.pascalx.view.alipay.rpc.PaymentRpcService;
import com.pascalx.view.alipay.rpc.PaymentRpcServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 利用同步工具countDownLatch实现
 *
 * @author yanghui
 * @date 2018/8/14.
 */
public class PaymentServiceCountDownLatchImpl extends AbstractPaymentService {


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
     * @param paymentTypes 支付类型
     * @param timeout      方法超时时间
     * @return 可用的支付类型列表, 如果无可用支付券列表或者入参支付类型为空，返回空list
     */
    @Override
    public List<String> enablePayments(List<String> paymentTypes, long timeout) {
        if (paymentTypes == null || paymentTypes.size() == 0) {
            return new ArrayList<>(0);
        }
        final CountDownLatch countDownLatch = new CountDownLatch(paymentTypes.size());
//        线程安全的list
        final List<String> enablePayments = Collections.synchronizedList(new LinkedList<String>());
//        提交线程池并发调用rpc
        for (String paymentType : paymentTypes) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    if (paymentRpcService.isEnabled(paymentType)) {
                        enablePayments.add(paymentType);
                    }
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
//            理论上没有主动中断的地方，如果线程被中断，继续当次返回
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
/*        线程池任务队列已满，且达到最大线程数，采用默认的拒绝策略，可能会报默认的RejectedExecutionException等，以及rpc调用网络异常等
 *         此处不需要处理异常，打印日志，直接返回正常可用的支付列表
 *         如果对方系统不稳定，可以考虑熔断策略
 */
        }

        return enablePayments;
    }

}
