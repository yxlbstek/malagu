package vip.malagu.app.service.impl;

import vip.malagu.app.param.dto.PayModel;
import vip.malagu.app.param.dto.PayParam;
import vip.malagu.app.service.PayOrderService;
import vip.malagu.app.service.PayService;
import vip.malagu.constants.CacheConstant;
import vip.malagu.enums.PayTypeEnum;
import vip.malagu.util.RedisUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayOrderServiceImpl implements PayOrderService {
    
    @Autowired
    private PayService payService;

    /**
     * 查询支付回调后、订单的支付状态
     * @param type 支付类型：2.支付宝、4.微信
     */
	@Override
	public void queryProcessingOrder(Integer type) {
		//支付回调后，缓存在Redis中的流水号
		String key = type == PayTypeEnum.ALI.getCode() ? CacheConstant.CACHE_PAY_SUCCESS_TRADENO_ALI_LIST : CacheConstant.CACHE_PAY_SUCCESS_TRADENO_WX_LIST;
		String tradeNo = (String) RedisUtils.leftPop(key);
		if (tradeNo != null) {
			try {
				// 第三方接口查询支付状态
				PayModel payModel = payService.payOrderQuery(new PayParam(type, tradeNo));
				if (payModel.isSuccess() && payModel.isTradeSuccess()) {
					
					//1.成功后，处理相应的业务逻辑
					
					//2.如果是跨境订单，保存支付信息，用于支付报关
					
				} else {
					RedisUtils.rightPush(key, tradeNo);
				}
			} catch (Exception e) {
				RedisUtils.rightPush(key, tradeNo);
				e.printStackTrace();
			}
		}
	}

}
