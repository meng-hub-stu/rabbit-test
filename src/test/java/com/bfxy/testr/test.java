package com.bfxy.testr;
import com.bfxy.rabbit.api.Message;
import com.bfxy.rabbit.api.MessageProducer;
import com.bfxy.rabbit.api.MessageType;
import com.bfxy.rabbit.api.SendCallback;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.UUID;

/**
 * @Author Mengdexin
 * @date 2022 -05 -02 -21:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class test {

    @Autowired
    private MessageProducer messageProducer;

    @Test
    public void testMessage() throws Exception{
        for (int i= 0 ;i < 1; i++) {
            String uuid = UUID.randomUUID().toString();
            Map<String, Object> attributes = Maps.newConcurrentMap();
            attributes.put("age", 12);
            attributes.put("name", "张三");
            Message message = new Message(
                    uuid,
                    "exchange-2",
                    "springboot.*",
                    attributes,
                    0,
                    MessageType.RELIANT);
            messageProducer.send(message/*, new SendCallback() {
                @Override
                public void onSuccess() {
                    System.out.println("---回调成功");
                }

                @Override
                public void onFailure() {
                    System.out.println("---回调失败");
                }
            }*/);
            System.out.println("发送消息结束");
        }
        Thread.sleep(100000);
    }

}
