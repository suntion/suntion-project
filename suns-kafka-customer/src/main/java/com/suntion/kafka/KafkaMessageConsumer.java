package com.suntion.kafka;

import com.sun.xml.internal.ws.api.message.MessageHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @program: suntion-project
 * @Description:
 * @Author: Shen.Sun
 * @create: 2020-04-02 17:32
 **/
@Component
public class KafkaMessageConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageConsumer.class);

    @KafkaListener(topics={"${kafka.app.topic.foo}"})
    public void receive(@Payload String message, @Headers MessageHeaders headers){
        LOG.info("KafkaMessageConsumer 接收到消息："+message);
    }
}
