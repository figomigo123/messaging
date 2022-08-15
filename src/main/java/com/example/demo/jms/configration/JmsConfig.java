package com.example.demo.jms.configration;

import com.example.demo.jms.broker.BrokerService;
import com.example.demo.jms.sender.ProducerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;

import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

@Configuration
@EnableJms
public class JmsConfig implements JmsListenerConfigurer {
    @Autowired
    BrokerService brokerService;
    ProducerFactory producerFactory = new ProducerFactory();
    List<String> queueNames = new ArrayList<>();

    @Override
    public void configureJmsListeners(JmsListenerEndpointRegistrar endpointRegistrar) {
        String queueName;
        try {
            queueNames = producerFactory.getInstanceFromPropertiesFiles();
            System.out.println("QueueNamesSize"+queueNames);
        } catch (IOException e1) {
          //   TODO Auto-generated catch block
            e1.printStackTrace();
       }
        for (int i = 0; i < queueNames.size(); i++) {
            SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
            queueName = "";
            queueName = queueNames.get(i);
            System.out.println("QueueName"+queueName);
            endpoint.setId(queueName);
            endpoint.setDestination(queueName);
            endpoint.setConcurrency("5-10");

            System.out.println(endpoint.getDestination());
            endpoint.setMessageListener(message -> {
                try {
                    brokerService.messageListenerAndForward(message);
                } catch (JMSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                }

            });
            endpointRegistrar.registerEndpoint(endpoint);

        }
    
}
}
