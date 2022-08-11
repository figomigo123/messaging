package com.example.demo.jms;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.jms.MessageProducer;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Producer  {

    private String name;
    private String description;
    private String id;

    public Producer(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "name: '" + this.name + "', description: '" + this.description +
                "', ID: '" + this.id;

    }

}
