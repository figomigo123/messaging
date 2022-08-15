package com.example.demo.jms.consumer;



import java.io.IOException;

import com.example.demo.jms.message.MyMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Consumer {

    private String name;
    private String description;
    private String id;
    private String subscriptions;
    private String type;
    private String propertiesFile;

   public Consumer(String name){
        this.name=name;
    }
    @Override
    public String toString() {
        return "name: '" + this.name + "' , description: '" + this.description + "', ID: '" + this.id
                + "' , subscriptions: '" + this.subscriptions + "' ,type: '" + this.type + "',propertiesFile: '"
                + this.propertiesFile + "'";

    }
    public void receiveMessage(MyMessage msg) throws IOException {
        System.out.println("die empfangene Nachricht:" + msg);
    }
    
}
/*

public interface Consumer {

    String name = null;
     String description = null;
     String id = null;
     String subscriptions = null;
     String type = null;
     String propertiesFile = null;


    public void receiveMessage(MyMessage msg) throws IOException;

}
 */