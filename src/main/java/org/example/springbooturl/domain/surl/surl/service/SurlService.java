package org.example.springbooturl.domain.surl.surl.service;


import lombok.RequiredArgsConstructor;
import org.example.springbooturl.domain.member.member.entity.Member;
import org.example.springbooturl.domain.surl.surl.dto.SurlDto;
import org.example.springbooturl.domain.surl.surl.entity.Surl;
import org.example.springbooturl.domain.surl.surl.event.SurlCommonEvent;
import org.example.springbooturl.domain.surl.surl.repository.SurlRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurlService {
    private final SurlRepository surlRepository;
    private final KafkaTemplate<Object, Object> template;

    public long count() {
        return surlRepository.count();
    }

    @Transactional
    public Surl create(Member author, String url, String title) {
        Surl surl = Surl.builder()
                .author(author)
                .url(url)
                .title(title)
                .body(url)
                .build();

        surlRepository.save(surl);

        SurlCommonEvent surlCommonEvent = new SurlCommonEvent("afterCreated", new SurlDto(surl));
        template.send("SurlCommonEvent", surlCommonEvent);
//        template.send("SurlAfterCreatedEvent", surlCommonEvent);

        return surl;
    }

    public Optional<Surl> findById(long id) {
        return surlRepository.findById(id);
    }

    @Transactional
    public void modify(Surl surl, String title) {
        surl.setTitle(title);
        surl.setModified();

        SurlCommonEvent surlCommonEvent = new SurlCommonEvent("afterModified", new SurlDto(surl));
        template.send("SurlCommonEvent", surlCommonEvent);
//        template.send("SurlAfterModifiedEvent", surlCommonEvent);
    }

    @Transactional
    public void delete(Surl surl) {
        SurlCommonEvent surlCommonEvent = new SurlCommonEvent("beforeDeleted", new SurlDto(surl));
        template.send("SurlCommonEvent", surlCommonEvent);
//        template.send("SurlBeforeDeletedEvent", surlCommonEvent);

        surlRepository.delete(surl);
    }
}
