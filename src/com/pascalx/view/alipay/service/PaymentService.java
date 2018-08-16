package com.pascalx.view.alipay.service;

import com.pascalx.view.alipay.service.Impl.AbstractPaymentService;

import java.util.List;

/**
 * @author yanghui
 * @date 2018/8/14.
 */
public interface PaymentService {

    /**
     * 对外提供的简单接口
     * 获取可用的支付列表
     * <p>
     * 串行的多个rpc调用，互相不依赖，一般可以采用异步并行或并发调用优化
     * jdk8 也新增了CompletableFuture 提供异步编程api,或者其它第三方的异步编程框架RxJava等
     * 我采用Future和CountDownLatch 结合线程池做了一下简单的两种实现
     *
     * @return 可用的支付列表
     * @see AbstractPaymentService 抽象类做了默认参数等方法，提供的service接口可根据需要依赖于它作更多扩展方法
     */
    List<String> enablePayments();
}
