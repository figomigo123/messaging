/*package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import com.example.demo.jms.MyConsumer;
import com.example.demo.jms.MyProducer;
import com.example.demo.jms.broker.MyBroker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrokerService {
    private List<Object> consumerObjList = new ArrayList<>();
    private List<Object> producerObjList = new ArrayList<>();

    public List<Object> 3bjList() {
        return this.consumerObjList;
    }

    public List<Object> getProducerObjList() {
        return this.producerObjList;
    }

    @PostConstruct
    public void initConsumerAndProducerlist() throws IOException {
        this.readConsumerProperties();
        this.readProducerProperties();
    }

    private void readConsumerProperties() throws IOException {

        Properties properties = searchProperties(); // hier wird den Inhalt von // application.properties
        // zurückgegeben // Pfad von MyConsumer-properties verzeichnis
        File directoryConsumer = new File(properties.getProperty("MyConsumer-properties.path")); // das Pfad in//
        // directoryConsumer    // öffnen


        String[] fileListConsumer = directoryConsumer.list(); // Inhalt von dem // Verzeichnis in flistConsumer
        // einfügen

        String str = "";
        Properties p = new Properties();
        for (int i = 0; i < fileListConsumer.length; i++) {
            str += properties.getProperty("MyConsumer-properties.path") + "/" + fileListConsumer[i]; // das gesamte   // Pfad // von   // jedem consumer   properties  file


            FileReader reader = new FileReader(str); // Auslesen
            p.load(reader); // laden
            MyConsumer myConsumer1 = new MyConsumer(p.getProperty("consumer.name"));
            myConsumer1.setId(p.getProperty("consumer.ID"));
            myConsumer1.setDescription(p.getProperty("Consumer.description"));
            myConsumer1.setSubscriptions(p.getProperty("Consumer.subscriptions"));
            myConsumer1.setType(p.getProperty("Consumer.type"));
            myConsumer1.setPropertiesFile(fileListConsumer[i]);
            consumerObjList.add(myConsumer1);

            System.getProperty("line.separator");

            str = "";
            p.clear();

        }
    }

    private void readProducerProperties() throws IOException {
        Properties properties = searchProperties(); // hier wird den Inhalt von
        // application.properties zurückgegeben
        File directoryProducer = new File(properties.getProperty("MyProducer-properties.path"));

        String[] fileListProducer = directoryProducer.list();
        String str = "";
        Properties p = new Properties();

        for (int i = 0; i < fileListProducer.length; i++) {
            str += properties.getProperty("MyProducer-properties.path") + "/" +
                    fileListProducer[i];

            FileReader reader = new FileReader(str);
            p.load(reader);
            MyProducer myProducer1 = new MyProducer(p.getProperty("producer.name"));
            myProducer1.setDescription(p.getProperty("producer.description"));
            myProducer1.setId(p.getProperty("producer.ID"));
            producerObjList.add(myProducer1);

            str = "";
            p.clear();
        }
    }

    public static Properties searchProperties() throws IOException {

        Properties props = new Properties();
        String propFileName = "application.properties";
        InputStream in = BrokerService.class.getClassLoader().getResourceAsStream(propFileName);
        if (in != null) {
            props.load(in);
        } else {
            throw new FileNotFoundException(propFileName + "not found");
        }

        return props;
    }



}*/


// Die Geschichte mit Hashmap (Vielleicht nicht mehr benötigt)
 /* @Autowired
    JmsTemplate jmsTemplate;
    private HashMap<String, List<String>> producerHashMap = new HashMap<>();*/

    /**
     * terms: <br/>
     * - producer is sender to broker <br/>
     * - broker is receiver from producer and sender to consumer <br/>
     * - consumer is receiver from broker <br/>
     *
     * @param myMessage: received message from queue 'my-first-queue'
     * @throws IOException
     * @implNote this method: <br/>
     *           - receives message<br/>
     *           - save message and senderId in HashMap list
     */

    /*
     * @JmsListener(destination = "second-Queue") // wird nicht mehr benötigt für
     * dynamisches binden diese Zeile
     * public void messageListener(Message message) {
     * 
     * List<String> producerMessages = producerHashMap.get(message.getProducerId());
     * // Suche in der Hashmap die richtige
     * // Liste raus
     * // falls die Liste für diesen Producer noch nicht angelegt ist --> anlegen
     * einer
     * // Liste
     * if (producerMessages == null) {
     * producerMessages = new ArrayList<>();
     * producerHashMap.put(message.getProducerId(), producerMessages);
     * }
     * producerMessages.add(message.getMessage()); // den Inhalt von der Message
     * 
     * // Testen : mehrere Nachrichten angekommen und angezeigt
     * System.out.println(producerHashMap.get("pro02"));
     * System.out.println(producerHashMap.get("pro01"));}
     */