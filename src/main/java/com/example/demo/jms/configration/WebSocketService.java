package com.example.demo.jms.configration;

import com.example.demo.jms.consumer.Consumer;
import com.example.demo.jms.consumer.ConsumerFactory;
import com.example.demo.jms.message.MyMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;
    private JmsTemplate jmsTemplate;

    public void notifyUser(final String userId, final MyMessage myMessage) {
        System.out.println("WebSocketService : ");
        for(Consumer consumer: ConsumerFactory.consumerObjList){
            System.out.println("WebSocketService : "+consumer.getId());
        this.send(consumer.getId(), myMessage);}
    }

    @SneakyThrows
    private void send(String userId, MyMessage myMessage) {
        String json = (new ObjectMapper()).writeValueAsString(myMessage);
        messagingTemplate.convertAndSendToUser(userId, "/topic/messages", json);
      //  jmsTemplate.(userId, "/topic/messages", json);
    }



}
