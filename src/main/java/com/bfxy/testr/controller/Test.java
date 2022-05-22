package com.bfxy.testr.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * @Author Mengdexin
 * @date 2022 -05 -04 -22:56
 */
@RestController
@ResponseBody
@Slf4j
public class Test implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback{

//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ConnectionFactory connectionFactory;

    @GetMapping(value = "test")
    public String send() {
        log.info("发送消息");
        String str = "发送一条消息";
        Message message = MessageBuilder
                .withBody(str.getBytes())
                .setContentEncoding(StandardCharsets.UTF_8.displayName())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.convertAndSend("exchange-1", "springboot-1.*", message);
        return "ok";
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        log.error("消息被退回:{}", message);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        log.error("消息被确认-1:{}", correlationData);
        log.error("ack->{}", b);
    }
}
