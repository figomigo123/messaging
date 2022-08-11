package com.example.demo.jms.sender;

import com.example.demo.jms.broker.BrokerService;
import com.example.demo.jms.consumer.FileWriterConsumer;
import com.example.demo.jms.message.MyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class WebController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private BrokerService brokerService;

    @PostMapping("/sendMessagetoFirstQueue")

    public ResponseEntity<?> publishMessageToFirstQueue(@RequestBody MyMessage myMessage) {

        jmsTemplate.convertAndSend("first-Queue", myMessage);


        return new ResponseEntity<>("sent", HttpStatus.OK);
    }

    @PostMapping("/sendMessagetoSecondQueue")
    public ResponseEntity<?> publishMessagetoSecondQueue(@RequestBody MyMessage myMessage) {
        jmsTemplate.convertAndSend("second-Queue", myMessage);
        return new ResponseEntity<>("sent", HttpStatus.OK);
    }

    @PostMapping("/consumer")
    public ResponseEntity<?> addConsumer(@RequestBody FileWriterConsumer consumer) {
        brokerService.addConsumer(consumer);
        return new ResponseEntity<>("created", HttpStatus.CREATED);
    }
 
    /*
     * @GetMapping("/MessagesList")
     * public List<String> getReceivedMessages() throws JMSException{
     * 
     * myBroker.messageListener(msg);
     * 
     * String id = msg.getProducerId();
     * return myBroker.getProducerHashMap().get(id);
     */
}
