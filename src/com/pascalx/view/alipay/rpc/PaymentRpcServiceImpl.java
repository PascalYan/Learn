package com.pascalx.view.alipay.rpc;

import com.pascalx.view.alipay.mock.ConsultResult;
import com.pascalx.view.alipay.mock.PaymentRemoteService;
import com.pascalx.view.alipay.mock.PaymentRemoteServiceLocalMockImpl;

/**
 * @author yanghui
 * @date 2018/8/14.
 */
public class PaymentRpcServiceImpl implements PaymentRpcService {
    private PaymentRemoteService paymentRemoteService = new PaymentRemoteServiceLocalMockImpl();

    @Override
    public boolean isEnabled(String paymentType) {
        try {
            // TODO: 2018/8/14 根据接口文档以及上游系统调用的需要，可能需要errorCode的处理
            ConsultResult result = paymentRemoteService.isEnabled(paymentType);
            return null != result && result.getIsEnable();
        } catch (Exception e) {
//            网络异常等
            return false;
        }
    }
}
