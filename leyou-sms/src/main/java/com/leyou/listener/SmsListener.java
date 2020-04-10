package com.leyou.listener;

import com.aliyuncs.exceptions.ClientException;
import com.leyou.config.SmsProperties;
import com.leyou.utils.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-24 19:02
 * @email : 1162864960@qq.com
 */
@Component
public class SmsListener {

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsProperties smsProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsListener.class);

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEYOU.SMS.QUEUE", durable = "true"),
            exchange = @Exchange(value = "LEYOU.SMS.EXCHANGE", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"verify.code"}
    ))
    public void sendSms(Map<String, String> msg) throws ClientException {
        if (CollectionUtils.isEmpty(msg)) {
            LOGGER.info("短信内容不能为空 : {}", msg);
            return;
        }
        String phone = msg.get("phone");
        String code = msg.get("code");
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(code)) {
            LOGGER.info("短信内容不能为空 : {},{}", phone, code);
            return;
        }
        this.smsUtils.sendSms(phone, code, this.smsProperties.getSignName(), this.smsProperties.getVerifyCodeTemplate());
    }
}
