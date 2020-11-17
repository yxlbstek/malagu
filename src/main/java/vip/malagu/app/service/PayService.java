package vip.malagu.app.service;

import java.util.Map;

import vip.malagu.app.param.dto.PayModel;
import vip.malagu.app.param.dto.PayParam;


public interface PayService {
	
    /**
     * 支付
     * @param query
     * @return
     */
    PayModel doPay(PayParam query);

    /**
     * 通知验签
     * @param query
     * @return
     * @throws Exception 
     */
    Map<String, String> notifySign(PayParam query) throws Exception;

    /**
     * 查询订单第三方支付状态
     * @param query
     * @return
     */
    PayModel payOrderQuery(PayParam query) throws Exception;

    /**
     * 退费
     * @param query
     * @return
     */
    PayModel doRefund(PayParam query);

    /**
     * 提现（转账功能）
     * @param query
     * @return
     */
    PayModel withdraw(PayParam query);

    /**
     * 支付报关
     * @param query payType & tradeNo & thirdNo & money & buyerName & buyerCertNo
     * @return
     */
    PayModel custom(PayParam query);

    /**
     * 支付报关查询
     * @param query
     * @return
     */
	PayModel customQuery(PayParam query);
	
}
