package org.example.springbooturl.domain.surl.surl.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.springbooturl.domain.surl.surl.entity.Surl;
import org.example.springbooturl.domain.surl.surl.repository.SurlRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SurlService {

    private final SurlRepository surlRepository;

    public long count() {
        return surlRepository.count();
    }

    @Transactional
    public Surl save(String url, String title) {
        Surl surl = Surl.builder()
                .url(url)
                .title(title)
                .build();

        surlRepository.save(surl);

        return surl;
    }
}
