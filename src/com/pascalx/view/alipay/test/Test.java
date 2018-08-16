package com.pascalx.view.alipay.test;


import com.pascalx.view.alipay.service.Impl.PaymentServiceCountDownLatchImpl;
import com.pascalx.view.alipay.service.Impl.PaymentServiceFutureImpl;
import com.pascalx.view.alipay.service.PaymentService;

/**
 * @author yanghui
 * @date 2018/8/14.
 */
public class Test {


    public static void main(String[] args) {
        //PaymentServiceCountDownLatchImpl test
        PaymentService countDownLatchImpl = new PaymentServiceCountDownLatchImpl();
        long countDownLatchImplStart = System.currentTimeMillis();
        System.out.println("PaymentServiceCountDownLatchImpl 可用的支付方式列表:" + countDownLatchImpl.enablePayments());
        System.out.println("PaymentServiceCountDownLatchImpl cost:" + (System.currentTimeMillis() - countDownLatchImplStart));


        //PaymentServiceFutureImpl test
        PaymentService futureImpl = new PaymentServiceFutureImpl();
        long futureImplStart = System.currentTimeMillis();
        System.out.println("PaymentServiceFutureImpl 可用的支付方式列表:" + futureImpl.enablePayments());
        System.out.println("PaymentServiceFutureImpl cost:" + (System.currentTimeMillis() - futureImplStart));

        System.exit(0);
    }

}
