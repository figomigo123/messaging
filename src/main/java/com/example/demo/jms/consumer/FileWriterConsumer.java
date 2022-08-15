package com.example.demo.jms.consumer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import com.example.demo.jms.message.MyMessage;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileWriterConsumer extends Consumer {

    private String path;

    @Override
    public String toString() {
        return super.toString() + "Ordner path:" + path;
    }

    @Override
    public void receiveMessage(MyMessage msg) throws IOException {
        
        FileWriter fw = new FileWriter("/Users/oula/Documents/Ordner/file.txt",true);
        System.out.println("Hallo from FileWriter");
        
        String msgString = String.valueOf(msg);  
     
        for (int i = 0; i < msgString.length(); i++) {
            fw.write(msgString.charAt(i));
        }
        fw.write(System.lineSeparator());
        System.out.println("Successfully written");
       

        // close the file
        fw.close();
    }

   

}
