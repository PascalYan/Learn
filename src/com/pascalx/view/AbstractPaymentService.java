package com.pascalx.view;

import java.util.Arrays;
import java.util.List;

/**
 * @author yanghui10
 * @date 2018/8/14.
 */
public abstract class AbstractPaymentService implements PaymentService {

    public List<String> enablePayments() {
        return enablePayments(Arrays.asList(new String[]{"余额", "红包", "优惠券", "代金券"}));
    }


    public abstract List<String> enablePayments(List<String> paymentTypes);

}
