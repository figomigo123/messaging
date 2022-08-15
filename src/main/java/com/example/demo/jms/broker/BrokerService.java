package com.example.demo.jms.broker;

import com.example.demo.jms.Producer;
import com.example.demo.jms.consumer.ConsoleWriterConsumer;
import com.example.demo.jms.consumer.Consumer;
import com.example.demo.jms.consumer.ConsumerFactory;
import com.example.demo.jms.consumer.FileWriterConsumer;
import com.example.demo.jms.message.MyMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BrokerService {

    private List<Consumer> consumerObjList = new ArrayList<>();
    private List<Producer> producerObjList = new ArrayList<>();
    ConsumerFactory consumerFactory = new ConsumerFactory();
    private final SimpMessagingTemplate messagingTemplate;

    public List<Consumer> getConsumerObjList() {
        return  consumerObjList = consumerFactory.consumerObjList;
    }

    public List<Producer> getProducerObjList() {
        return this.producerObjList;
    }

    

    @PostConstruct
    public void initConsumerAndProducerlist() throws IOException {
        consumerFactory.getInstanceFromPropertiesFiles();
    }

    public void messageListenerAndForward(Message message) throws JMSException, IOException {
        FileWriterConsumer fileWriterConsumer = new FileWriterConsumer();
        ConsoleWriterConsumer consoleWriterConsumer = new ConsoleWriterConsumer();

        // JMS Nachricht zu MyMessage konvertieren
        SimpleMessageConverter converter = new SimpleMessageConverter();
        MyMessage msg = (MyMessage) converter.fromMessage(message);

        System.out.println(msg.toString());

        String id = msg.getProducerId(); // producerId von dem Message in einer Variable speichern

        System.out.println(consumerObjList.size()); // Liste testen , ob schon objekte angelegt wurden

        String subscriptionId = "";
        Boolean producerIdExist = false;

        // Forschleife zum vergleichen das producerId mit dem
        // subscriptionId in den Consumer Objekte
        this.consumerObjList = ConsumerFactory.consumerObjList;

        for (int i = 0; i < consumerObjList.size(); i++) {
            System.out.println(consumerObjList.size());
            // Zwischenspeicher
            Consumer cons = consumerObjList.get(i); // casten von objekt in einer list zu Consumer Objekt
            subscriptionId = cons.getSubscriptions(); // subscriptionId enthält die subscription Liste

            if (subscriptionId.contains(id)) { // ob die subscriptionId enhält das ProducerId
                producerIdExist = true;
                System.out.println(
                        "Diese Nachricht soll zu dem Consumer mit dem ID:" + cons.getId() + " weitergeleitet werden");
                if (cons.getType().equals("FileWriterConsumer")) {
                    fileWriterConsumer.receiveMessage(msg);

                } else if (cons.getType().equals("ConsoleWriterConsumer")) {
                    consoleWriterConsumer.receiveMessage(msg);
                } else if (cons.getType().equals("WebSocket")) {
                    String json = (new ObjectMapper()).writeValueAsString(msg);
                    messagingTemplate.convertAndSendToUser(consumerObjList.get(i).getId(), "/topic/messages", json);
                    //  jmsTemplate.(userId, "/topic/messages", json);
                }
            }
            subscriptionId = "";
        }
        if (!producerIdExist) {
            System.out.println("keine passende Consumer gefunden");
        }
        
    }


  

     
}