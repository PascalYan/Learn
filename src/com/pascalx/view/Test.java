package com.pascalx.view;

import java.util.Arrays;

/**
 * @author yanghui10
 * @date 2018/8/14.
 */
public class Test {


    public static void main(String[] args) {
        PaymentService paymentService = new ThreadPoolImpl();

        long start = System.currentTimeMillis();
        System.out.println("可用的支付方式列表:" + paymentService.enablePayments());
        System.out.println("cost:" + (System.currentTimeMillis() - start));
    }

}
