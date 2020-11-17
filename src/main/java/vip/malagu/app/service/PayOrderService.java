package vip.malagu.app.service;

public interface PayOrderService {
	
    /**
     * 查询支付回调后、订单的支付状态
     * @param type 支付类型：2.支付宝、4.微信
     */
    void queryProcessingOrder(Integer type);

}
