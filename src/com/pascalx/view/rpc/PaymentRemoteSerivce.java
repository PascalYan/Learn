package com.pascalx.view.rpc;

import java.util.concurrent.TimeUnit;

/**
 * @author yanghui10
 * @date 2018/8/14.
 */
public class PaymentRemoteSerivce {


    public ConsultResult isEnabled(String paymentType) {
        try {
            //模拟rpc调用耗时，0-100ms
            long costTime = (long) (Math.random() * 100);
            System.out.println(paymentType+" isEnabled invoke rpc cost time :" + costTime + "ms");
            TimeUnit.MILLISECONDS.sleep(costTime);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //随机模拟是否可用的结果
        return Math.random() > 0.8 ? new ConsultResult(true, "0000") : new ConsultResult(false, "9999");
    }
}
