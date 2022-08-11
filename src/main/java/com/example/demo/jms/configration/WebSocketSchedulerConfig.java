package com.example.demo.jms.configration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
public class WebSocketSchedulerConfig {

    @Autowired
    private SimpMessagingTemplate template;


    @Scheduled(fixedRate = 3000)
    public void publishUpdates() {
        System.out.println("Message: " );
        template.convertAndSend("/test/websocket", "your message is send");
    }

}