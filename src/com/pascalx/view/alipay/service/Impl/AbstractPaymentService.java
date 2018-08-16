package com.pascalx.view.alipay.service.Impl;

import com.pascalx.view.alipay.service.PaymentService;

import java.util.Arrays;
import java.util.List;

/**
 * @author yanghui
 * @date 2018/8/14.
 */
public abstract class AbstractPaymentService implements PaymentService {

    private static final long DEFAULT_TIMEOUT = 50;

    public List<String> enablePayments() {
//        模拟下不同的支付类型
        return enablePayments(Arrays.asList(new String[]{"余额", "红包", "优惠券", "代金券"}));
    }


    /**
     * @param paymentTypes 支付类型,超时时间取默认值 DEFAULT_TIMEOUT
     * @return 可用的支付类型列表, 如果无可用支付券列表或者入参支付类型为空，返回空list
     */
    public List<String> enablePayments(List<String> paymentTypes) {
        return enablePayments(paymentTypes, DEFAULT_TIMEOUT);
    }

    /**
     * @param paymentTypes 支付类型
     * @param timeout      方法超时时间
     * @return 可用的支付类型列表, 如果无可用支付券列表或者入参支付类型为空，返回空list
     */
    public abstract List<String> enablePayments(List<String> paymentTypes, long timeout);

}
