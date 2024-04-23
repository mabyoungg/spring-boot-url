package org.example.springbooturl.domain.surl.surlDocument.eventListner;

import lombok.RequiredArgsConstructor;
import org.example.springbooturl.domain.surl.surl.event.SurlCommonEvent;
import org.example.springbooturl.domain.surl.surlDocument.service.SurlDocumentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SurlDocumentEventListener {
    private final SurlDocumentService surlDocumentService;

    @KafkaListener(topics = "SurlCommonEvent", groupId = "1")
    public void consumeSurlCommonEvent(SurlCommonEvent surlCommonEvent) {
        System.out.println("surlCommonEvent: " + surlCommonEvent);

        switch (surlCommonEvent.getEventType()) {
            case "afterCreated" -> surlDocumentService.add(surlCommonEvent.getSurlDto());
            case "afterModified" -> surlDocumentService.modify(surlCommonEvent.getSurlDto());
            case "beforeDeleted" -> surlDocumentService.delete(surlCommonEvent.getSurlDto());
        }
    }

    @KafkaListener(topics = "SurlCommonEvent.DLT", groupId = "1")
    public void consumeSurlCommonEventDLT(byte[] in) {
        String message = new String(in);
        System.out.println("SurlCommonEvent.DLT: failed message: " + message);
    }

    @KafkaListener(topics = "SurlAfterCreatedEvent", groupId = "1")
    public void consumeSurlAfterCreatedEvent(SurlCommonEvent surlCommonEvent) {
        // 추가적으로 특별한일이 있으면 처리
//        surlDocumentService.add(surlCommonEvent.getSurlDto());
    }

    @KafkaListener(topics = "SurlAfterCreatedEvent.DLT", groupId = "1")
    public void consumeSurlAfterCreatedEventDLT(byte[] in) {
        String message = new String(in);
        System.out.println("SurlAfterCreatedEvent.DLT: failed message: " + message);
    }

    @KafkaListener(topics = "SurlAfterModifiedEvent", groupId = "1")
    public void consumeSurlAfterModifiedEvent(SurlCommonEvent surlCommonEvent) {
        // 추가적으로 특별한일이 있으면 처리
//        surlDocumentService.modify(surlCommonEvent.getSurlDto());
    }

    @KafkaListener(topics = "SurlAfterModifiedEvent.DLT", groupId = "1")
    public void consumeSurlAfterModifiedEventDLT(byte[] in) {
        String message = new String(in);
        System.out.println("SurlAfterModifiedEvent.DLT: failed message: " + message);
    }

    @KafkaListener(topics = "SurlBeforeDeletedEvent", groupId = "1")
    public void consumeSurlBeforeDeletedEvent(SurlCommonEvent surlCommonEvent) {
        // 추가적으로 특별한일이 있으면 처리
//        surlDocumentService.delete(surlCommonEvent.getSurlDto());
    }

    @KafkaListener(topics = "SurlBeforeDeletedEvent.DLT", groupId = "1")
    public void consumeSurlBeforeDeletedEventDLT(byte[] in) {
        String message = new String(in);
        System.out.println("SurlBeforeDeletedEvent.DLT: failed message: " + message);
    }
}