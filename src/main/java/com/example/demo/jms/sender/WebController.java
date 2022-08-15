package com.example.demo.jms.sender;

import com.example.demo.jms.broker.BrokerService;

import com.example.demo.jms.configration.WebSocketService;
import com.example.demo.jms.consumer.Consumer;
import com.example.demo.jms.consumer.ConsumerFactory;
import com.example.demo.jms.message.MyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/test")
public class WebController {
    @Autowired
    WebSocketService webSocketService;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private BrokerService brokerService;

    @PostMapping("/sendMessagetoFirstQueue")
    public ResponseEntity<?> publishMessageToFirstQueue(@RequestBody MyMessage myMessage) {
        jmsTemplate.convertAndSend("first-Queue", myMessage);
       // webSocketService.notifyUser("first-Queue",myMessage);
        System.out.println(myMessage.toString());

        return new ResponseEntity<>("sent", HttpStatus.OK);
    }

    @PostMapping("/sendMessagetoSecondQueue")
    public ResponseEntity<?> publishMessagetoSecondQueue(@RequestBody MyMessage myMessage) {
        jmsTemplate.convertAndSend("second-Queue", myMessage);
        return new ResponseEntity<>("sent", HttpStatus.OK);
    }

    @PostMapping("/consumer")
    public ResponseEntity<?> addConsumer(@RequestBody Map<String,Object> consumerInfo) {
       ConsumerFactory.getInstanceFromWebInterface(consumerInfo);
        return new ResponseEntity<>("created", HttpStatus.CREATED);
    }


    @GetMapping("/ConsumersList")
    public List<Consumer> getConsumerList() {

        return brokerService.getConsumerObjList();

    }

}
