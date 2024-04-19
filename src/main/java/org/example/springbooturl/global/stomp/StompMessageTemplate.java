package org.example.springbooturl.global.stomp;

public interface StompMessageTemplate {
    void convertAndSend(String type, String destination, Object payload);
}
