package com.example.demo.jms.message;
import java.io.Serializable;
import java.util.Calendar;
import lombok.*;

@Getter
@Setter
public class MyMessage implements Serializable {
    private String producerId;
    private String message;
    private String msgModule;
    private int msgPriority;
    private String msgLevel;
    private String msgSessionId;
    Calendar msgTime = Calendar.getInstance();

    @Override
    public String toString() {
        return "{" + "source:'" + producerId + '\'' + ",msgModule:'" + msgModule + '\''
                + ", msgPriority:'" + msgPriority + '\'' + ",msgLevel:'" + msgLevel + '\'' + ",msgSessionId:'"
                + msgSessionId + '\''
                + ", message:'" + message + '\''
                + ",msgTime:" + msgTime.get(Calendar.HOUR_OF_DAY) + ":" + msgTime.get(Calendar.MINUTE) + " "  //andere Funktion
                + msgTime.get(Calendar.DAY_OF_MONTH) + "/" + (msgTime.get(Calendar.MONTH) + 1) + "/"
                + msgTime.get(Calendar.YEAR) + "}";

    }

    public char[] charAt(int i) {
        return null;
    }
}
