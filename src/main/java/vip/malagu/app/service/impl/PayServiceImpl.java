package vip.malagu.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.response.*;

import vip.malagu.app.param.dto.PayModel;
import vip.malagu.app.param.dto.PayParam;
import vip.malagu.app.service.PayService;
import vip.malagu.common.sdk.alipay.AlipaySdkService;
import vip.malagu.common.sdk.alipay.model.AlipayAcquireCustomsResponse;
import vip.malagu.common.sdk.alipay.model.AlipayCustomsQueryResponse;
import vip.malagu.common.sdk.wechat.WeChatApiService;
import vip.malagu.enums.PayModelTypeEnum;
import vip.malagu.enums.PayTypeEnum;
import vip.malagu.enums.SystemErrorEnum;
import vip.malagu.enums.WXPayTradeTypeEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private AlipaySdkService alipaySdkService;

    @Autowired
    private WeChatApiService weChatApiService;

    /**
     * 支付接口
     * @param param payType & tradeNo & subject & totalAmount & tradeType & userId
     * @return
     */
    @Override
    public PayModel doPay(PayParam param){
        switch (PayTypeEnum.getByCode(param.getPayType())){
            case YU_E:
            	return PayModel.builder()
                        .way(PayTypeEnum.YU_E.getCode())
                        .type(PayModelTypeEnum.PAY.getCode())
                        .code(SystemErrorEnum.SUCCESS.getStatus())
                        .msg(SystemErrorEnum.SUCCESS.getMessage())
                        .subCode(null)
                        .thirdNo(null)
                        .tradeType(param.getTradeType())
                        .tradeNo(param.getTradeNo())
                        .money(Double.toString(param.getTotalAmout().doubleValue()))
                        .body(null)
                        .build();
            case ALI:
            	if(param.getTradeType() == null || param.getTradeType() == WXPayTradeTypeEnum.APP.getCode()) {
            		AlipayTradeAppPayResponse response = alipaySdkService.aliPay(param.getTradeNo(), param.getSubject(), param.getTotalAmout());
                    return PayModel.builder()
                            .way(PayTypeEnum.ALI.getCode())
                            .type(PayModelTypeEnum.PAY.getCode())
                            .code(response.getCode())
                            .msg(response.getMsg())
                            .subCode(response.getSubCode())
                            .thirdNo(response.getTradeNo())
                            .tradeType(param.getTradeType())
                            .tradeNo(response.getOutTradeNo())
                            .money(response.getTotalAmount())
                            .body(response.getBody())
                            .requestMsg(response.getBody())
                            .build();
            	} else {
            		AlipayTradePagePayResponse response = alipaySdkService.aliPayForPc(param.getTradeNo(), param.getSubject(), param.getTotalAmout(), param.getReturnUrl());
                    return PayModel.builder()
                            .way(PayTypeEnum.ALI.getCode())
                            .type(PayModelTypeEnum.PAY.getCode())
                            .code(response.getCode())
                            .msg(response.getMsg())
                            .subCode(response.getSubCode())
                            .thirdNo(response.getTradeNo())
                            .tradeType(param.getTradeType())
                            .tradeNo(response.getOutTradeNo())
                            .money(response.getTotalAmount())
                            .body(response.getBody())
                            .requestMsg(response.getBody())
                            .build();
            	}
            case ALI_H5:
            	AlipayTradeWapPayResponse resp = alipaySdkService.aliH5Pay(param.getTradeNo(), param.getSubject(), param.getTotalAmout(),param.getReturnUrl());
                return PayModel.builder()
                        .way(PayTypeEnum.ALI_H5.getCode())
                        .type(PayModelTypeEnum.PAY.getCode())
                        .code(resp.getCode())
                        .msg(resp.getMsg())
                        .subCode(resp.getSubCode())
                        .thirdNo(resp.getTradeNo())
                        .tradeType(param.getTradeType())
                        .tradeNo(resp.getOutTradeNo())
                        .money(resp.getTotalAmount())
                        .body(resp.getBody())
                        .requestMsg(resp.getBody())
                        .build();
            case WECHAT:
                Map<String,String> result = weChatApiService.wxPay(param.getTradeNo()
                		,param.getSubject(),param.getTotalAmout()
                		,param.getTradeType(), param.getOpenId(), param.getProductId());
                String requestMsg = result.get("requestMsg");
                result.remove("requestMsg");
                return PayModel.builder()
                        .way(PayTypeEnum.WECHAT.getCode())
                        .type(PayModelTypeEnum.PAY.getCode())
                        .code(result.get("return_code"))
                        .msg(result.get("return_msg"))
                        .subCode(result.get("result_code"))
                        .tradeType(param.getTradeType())
                        .tradeNo(param.getTradeNo())
                        .money(param.getTotalAmout().toString())
                        .body(JSONObject.toJSONString(result))
                        .requestMsg(requestMsg)
                        .build();
            case WECHAT_H5:
                Map<String,String> resultMap = weChatApiService.wxPay(param.getTradeNo(),param.getSubject(),param.getTotalAmout(),param.getTradeType(), param.getOpenId(), null);
                String msg = resultMap.get("requestMsg");
                resultMap.remove("requestMsg");
                return PayModel.builder()
                        .way(PayTypeEnum.WECHAT_H5.getCode())
                        .type(PayModelTypeEnum.PAY.getCode())
                        .code(resultMap.get("return_code"))
                        .msg(resultMap.get("return_msg"))
                        .subCode(resultMap.get("result_code"))
                        .tradeType(param.getTradeType())
                        .tradeNo(param.getTradeNo())
                        .money(param.getTotalAmout().toString())
                        .body(JSONObject.toJSONString(resultMap))
                        .requestMsg(msg)
                        .build();
        }
        return null;
    }

    /**
     * 异步通知验签
     * @param param payType & request & response
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("incomplete-switch")
	@Override
    public Map<String,String> notifySign(PayParam param) throws Exception{
        switch (PayTypeEnum.getByCode(param.getPayType())){
            case YU_E:
                return null;
            case ALI:
                return alipaySdkService.result(param.getRequest(),param.getResponse());
            case WECHAT:
                return weChatApiService.notify(param.getRequest(),param.getResponse());
        }
        return null;
    }

    /**
     * 支付查询
     * @param param payType & tradeNo
     * @return
     */
    @SuppressWarnings("incomplete-switch")
	@Override
    public PayModel payOrderQuery(PayParam param) throws Exception {
        switch (PayTypeEnum.getByCode(param.getPayType())){
            case YU_E:
                return null;
            case ALI:
                AlipayTradeQueryResponse response = alipaySdkService.tradezResult(param.getTradeNo());
                return PayModel.builder()
                        .way(PayTypeEnum.ALI.getCode())
                        .type(PayModelTypeEnum.PAY_QUERY.getCode())
                        .code(response.getCode())
                        .msg(response.getMsg())
                        .subCode(response.getSubCode())
                        .thirdNo(response.getTradeNo())
                        .tradeNo(response.getOutTradeNo())
                        .tradeStatus(response.getTradeStatus())
                        .money(response.getTotalAmount())
                        .body(response.getBody())
                        .build();
            case WECHAT:
                Map<String,String> result = weChatApiService.payOrderQuery(param.getTradeNo());
                return PayModel.builder()
                        .way(PayTypeEnum.WECHAT.getCode())
                        .type(PayModelTypeEnum.PAY_QUERY.getCode())
                        .code(result.get("return_code"))
                        .msg(result.get("return_msg"))
                        .subCode(result.get("result_code"))
                        .tradeNo(param.getTradeNo())
                        .thirdNo(result.get("transaction_id"))
                        .tradeStatus(result.get("trade_state"))
                        .body(JSONObject.toJSONString(result))
                        .build();
        }
        return null;
    }

    /**
     * 退款接口
     * @param param payType & tradeNo & totalAmout & refundTradeNo & userId
     * @return
     */
    @SuppressWarnings("incomplete-switch")
	@Override
    public PayModel doRefund(PayParam param){
        switch (PayTypeEnum.getByCode(param.getPayType())){
            case YU_E:
            	return PayModel.builder()
                        .way(PayTypeEnum.YU_E.getCode())
                        .type(PayModelTypeEnum.REFUND.getCode())
                        .code(SystemErrorEnum.SUCCESS.getStatus())
                        .msg(SystemErrorEnum.SUCCESS.getMessage())
                        .subCode(null)
                        .thirdNo(null)
                        .tradeNo(param.getTradeNo())
                        .money(Double.toString(param.getMoney().doubleValue()))
                        .body(null)
                        .build();
            case ALI:
                AlipayTradeRefundResponse response = alipaySdkService.refund(param.getTradeNo(), param.getMoney(), param.getRefundTradeNo());
                return PayModel.builder()
                        .way(PayTypeEnum.ALI.getCode())
                        .type(PayModelTypeEnum.REFUND.getCode())
                        .code(response.getCode())
                        .msg(response.getMsg())
                        .subCode(response.getSubCode())
                        .thirdNo(response.getTradeNo())
                        .tradeNo(response.getOutTradeNo())
                        .money(response.getRefundFee())
                        .body(response.getBody())
                        .build();
            case WECHAT:
                Map<String,String> result = weChatApiService.refund(param.getTradeNo(),param.getTotalAmout(),param.getMoney(),param.getRefundTradeNo());
                return PayModel.builder()
                        .way(PayTypeEnum.WECHAT.getCode())
                        .type(PayModelTypeEnum.REFUND.getCode())
                        .code(result.get("return_code"))
                        .msg(result.get("return_msg"))
                        .subCode(result.get("result_code"))
                        .thirdNo(result.get("transaction_id"))
                        .thirdRefundNo(result.get("out_refund_no"))
                        .tradeNo(result.get("out_trade_no"))
                        .money(result.get("refund_fee"))
                        .body(JSONObject.toJSONString(result))
                        .build();
        }
        return null;
    }

    /**
     * 提现（转账功能）
     * @param param payType & tradeNo & thirdUsername & money & subject
     * @return
     */
    @SuppressWarnings("incomplete-switch")
	@Override
    public PayModel withdraw(PayParam param){
        switch (PayTypeEnum.getByCode(param.getPayType())){
            case YU_E:
                return null;
            case ALI:
                AlipayFundTransToaccountTransferResponse response = alipaySdkService.transfer(param.getTradeNo()
                        , param.getThirdUsername()
                        , param.getTotalAmout()
                        , param.getSubject());
                return PayModel.builder()
                        .way(PayTypeEnum.ALI.getCode())
                        .type(PayModelTypeEnum.PAY.getCode())
                        .code(response.getCode())
                        .msg(response.getMsg())
                        .subCode(response.getSubCode())
                        .thirdNo(response.getOrderId())
                        .tradeNo(response.getOutBizNo())
                        .money(response.getParams().get("amount"))
                        .body(response.getBody())
                        .build();
            case WECHAT:
                return null;
        }
        return null;
    }


    /**
     * 支付报关
     * @param param payType & tradeNo & thirdNo & money & buyerName & buyerCertNo
     * @return
     */
    @SuppressWarnings("incomplete-switch")
	@Override
    public PayModel custom(PayParam param){
    	PayModel payModel = null;
        switch (PayTypeEnum.getByCode(param.getPayType())){
            case ALI:
                AlipayAcquireCustomsResponse response =
                        alipaySdkService.custom(param.getTradeNo(), param.getThridNo(), param.getMoney()
                                , param.getBuyerName(), param.getBuyerCertNo());
                payModel = PayModel.builder()
                        .way(PayTypeEnum.ALI.getCode())
                        .type(PayModelTypeEnum.PAY.getCode())
                        .code(response.getCode())
                        .msg(response.getMsg())
                        .subCode(response.getSubCode())
                        .thirdNo(response.getPayTransactionId())
                        .tradeNo(param.getTradeNo())
                        .money(response.getTotalAmount())
                        .body(response.getBody())
                        .tradeStatus(response.getMsg().equals("T") ? "TRADE_SUCCESS" : "TRADE_FAIL")
                        .build();
                break;
            case WECHAT:
                Map<String,String> result = weChatApiService.custom(param.getTradeNo(), param.getThridNo(), param.getMoney()
                        , param.getBuyerName(), param.getBuyerCertNo(), param.getRePush());
                payModel = PayModel.builder()
                        .way(PayTypeEnum.WECHAT.getCode())
                        .type(PayModelTypeEnum.PAY.getCode())
                        .code(result.get("return_code"))
                        .msg(result.get("return_msg"))
                        .subCode(result.get("result_code"))
                        .tradeNo(param.getTradeNo())
                        .thirdNo(result.get("verify_department_trade_id"))
                        .money(param.getMoney().toString())
                        .body(JSONObject.toJSONString(result))
                        .tradeStatus(result.get("cert_check_result").equals("SAME") ? "SUCCESS" : "FAIL")
                        .build();
                break;
        }
        return payModel;
    }
    

    /**
     * 支付报关查询
     * @param param payType & tradeNo
     * @return
     */
    @SuppressWarnings("incomplete-switch")
	@Override
    public PayModel customQuery(PayParam param){
    	PayModel payModel = null;
        switch (PayTypeEnum.getByCode(param.getPayType())){
            case ALI:
                AlipayCustomsQueryResponse response =
                        alipaySdkService.customQuery(param.getTradeNo());
                payModel = PayModel.builder()
                        .way(PayTypeEnum.ALI.getCode())
                        .type(PayModelTypeEnum.PAY.getCode())
                        .code(response.getCode())
                        .msg(response.getMsg())
                        .subCode(response.getSubCode())
                        .tradeNo(param.getTradeNo())
                        .money(response.getAmount())
                        .body(response.getBody())
                        .tradeStatus(response.getStatus().equals("succ") ? "TRADE_SUCCESS" : "TRADE_FAIL")
                        .build();
                break;
            case WECHAT:
                Map<String,String> result = weChatApiService.customQuery(param.getTradeNo());
                payModel = PayModel.builder()
                        .way(PayTypeEnum.WECHAT.getCode())
                        .type(PayModelTypeEnum.PAY.getCode())
                        .code(result.get("return_code"))
                        .msg(result.get("return_msg"))
                        .subCode(result.get("result_code"))
                        .tradeNo(param.getTradeNo())
                        .thirdNo(result.get("transaction_id"))
                        .tradeStatus("SUCCESS".equals(result.get("state_0")) ? "SUCCESS" : "FAIL")
//                        .tradeStatus("SAME".equals(result.get("cert_check_result_0")) ? "SUCCESS" : "FAIL")
//                        .money(param.getMoney().toString())
                        .body(JSONObject.toJSONString(result))
                        .build();
                break;
        }
        return payModel;
    }

}
