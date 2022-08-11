package com.example.demo.jms.broker;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;

import com.example.demo.jms.consumer.Consumer;
import com.example.demo.jms.consumer.FileWriterConsumer;
import com.example.demo.jms.message.MyMessage;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@Service
public class BrokerService {

    private List<Object> consumerObjList = new ArrayList<>();
    private List<Object> producerObjList = new ArrayList<>();

    public List<Object> getConsumerObjList() {
        return this.consumerObjList;
    }

    public List<Object> getProducerObjList() {
        return this.producerObjList;
    }

    @PostConstruct
    public void initConsumerAndProducerlist() throws IOException {
        this.readConsumerProperties();
        // this.readProducerProperties();
    }

    private void readConsumerProperties() throws IOException {

        Properties properties = searchProperties(); // hier wird den Inhalt von //
        // application.properties
        // zurückgegeben // Pfad von MyConsumer-properties verzeichnis
        File directoryConsumer = new File(properties.getProperty("Consumer-properties.path")); // das Pfad in//
        // directoryConsumer // öffnen

        String[] fileListConsumer = directoryConsumer.list(); // Inhalt von dem //
        // Verzeichnis in flistConsumer
        // einfügen

        String str = "";
        Properties p = new Properties();
        for (int i = 0; i < fileListConsumer.length; i++) {
            str += properties.getProperty("Consumer-properties.path") + "/" +
                    fileListConsumer[i]; // das gesamte Pfad von // jedem consumer // properties file

            FileReader reader = new FileReader(str); // Auslesen
            p.load(reader); // laden
            if (p.getProperty("consumer.type").equals("FileWriterConsumer")) {
                FileWriterConsumer fileWriterConsumer = new FileWriterConsumer();
                fileWriterConsumer.setPath(p.getProperty("consumer.path"));
                fileWriterConsumer.setId(p.getProperty("consumer.ID"));
                fileWriterConsumer.setDescription(p.getProperty("consumer.description"));
                fileWriterConsumer.setSubscriptions(p.getProperty("consumer.subscriptions"));
                fileWriterConsumer.setType(p.getProperty("consumer.type"));
                fileWriterConsumer.setName(p.getProperty("consumer.name"));
                fileWriterConsumer.setPropertiesFile(fileListConsumer[i]);
                consumerObjList.add(fileWriterConsumer);
                System.out.println(fileWriterConsumer.toString());
            } else {
                Consumer consumer1 = new Consumer(p.getProperty("consumer.name"));
                consumer1.setId(p.getProperty("consumer.ID"));
                consumer1.setDescription(p.getProperty("consumer.description"));
                consumer1.setSubscriptions(p.getProperty("consumer.subscriptions"));
                consumer1.setType(p.getProperty("consumer.type"));
                consumer1.setPropertiesFile(fileListConsumer[i]);
                consumerObjList.add(consumer1);

            }
            str = "";
            p.clear();
        }
    }

    FileWriterConsumer fileWriterConsumer = new FileWriterConsumer();

    public void messageListener(Message message) throws JMSException, IOException {

        // JMS Nachricht zu MyMessage konvertieren
        SimpleMessageConverter converter = new SimpleMessageConverter();
        MyMessage msg = (MyMessage) converter.fromMessage(message);

        System.out.println(msg.toString());

        String id = msg.getProducerId(); // producerId von dem Message in einer Variable speichern

        System.out.println(consumerObjList.size()); // Liste testen , ob schon objekte angelegt wurden

        String subscriptionId = "";
        Boolean producerIdExist = false;
        for (int i = 0; i < consumerObjList.size(); i++) { // Forschleife zum vergleichen das producerId mit dem
                                                           // subscriptionId in den Consumer Objekte

            // cons = new Consumer(); // Zwischenspeicher
             Consumer cons = (Consumer) consumerObjList.get(i); // casten von objekt in einer list zu Consumer Objekt
            subscriptionId = cons.getSubscriptions(); // subscriptionId enthält die subscription Liste

            if (subscriptionId.contains(id)) { // ob die subscriptionId enhält das ProducerId
                producerIdExist = true;
                System.out.println(
                        "Diese Nachricht soll zu dem Consumer mit dem ID:" + cons.getId() + " weitergeleitet werden");
                if (cons.getType().equals("FileWriterConsumer")) {
                    fileWriterConsumer.receiveMessage(msg);

                }

            }
            subscriptionId = "";
        }
        if (!producerIdExist) {
            System.out.println("keine passende Consumer gefunden");
        }

    }

    public List<String> readProducerProperties() throws IOException {
        Properties properties = searchProperties(); // hier wird den Inhalt von
        // application.properties zurückgegeben
        File directoryProducer = new File(properties.getProperty("Producer-properties.path"));

        String[] fileListProducer = directoryProducer.list();

        List<String> queueNames = new ArrayList<>();

        String str = "";
        Properties p = new Properties();

        for (int i = 0; i < fileListProducer.length; i++) {
            str += properties.getProperty("Producer-properties.path") + "/" +
                    fileListProducer[i];

            FileReader reader = new FileReader(str);
            p.load(reader);
            /*
             * Producer producer1 = new Producer(p.getProperty("producer.name"));
             * producer1.setDescription(p.getProperty("producer.description"));
             * producer1.setId(p.getProperty("producer.ID"));
             * producerObjList.add(myProducer1);
             * // Queue name in eine liste einfügen
             */
            queueNames.add(p.getProperty("producer.msg_source"));

            str = "";
            p.clear();
        }
        return queueNames;
    }

    public Properties searchProperties() throws IOException {

        Properties props = new Properties();
        String propFileName = "application.properties";
        InputStream in = BrokerService.class.getClassLoader().getResourceAsStream(propFileName);
        if (in != null) {
            props.load(in);
        } else {
            System.out.println("");
        }

        return props;
    }

    public void addConsumer(FileWriterConsumer consumer) {
        Consumer consumer1 =null;
if(consumer.getType().equals("")) {
    consumer1 =  consumer;
    consumerObjList.add(consumer1);
}else  consumerObjList.add(consumer);





    }
}

// store producer and his message:

// get all producers and consumers from app-properties:
// ArrayList<MyProducer> producers = getProducersFromProperties(); // all
// producers
// ArrayList<MyConsumer> consumers = getConsumersFromProperties(); // all
// consumers
// get id of producer:

// TODO: get producer-object from producers:
// create producer-object:
// myMessage.setProducerId(producerId);
// MyProducer producer = new MyProducer(producerId);

// TODO: get all subscribed consumers from consumers:

/*
 * TODO:
 * store the received message with producer in a HashMap-List:
 * - create new HashMap:
 * HashMap<MyProducer, MyMessage> myList = new HashMap<>();
 * - create producerObject
 * - put new couple in HashMap-list:
 * myList.put(producerId, myMessage);
 * select consumer:
 * - get all subscribed consumers
 * - store consumers list in ArrayList:
 * ArrayList<MyConsumer> consumers = new ArrayList<>();
 */
