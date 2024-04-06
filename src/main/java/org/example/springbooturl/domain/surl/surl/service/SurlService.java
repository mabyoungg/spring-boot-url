package org.example.springbooturl.domain.surl.surl.service;


import lombok.RequiredArgsConstructor;
import org.example.springbooturl.domain.surl.surl.entity.Surl;
import org.example.springbooturl.domain.surl.surl.repository.SurlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SurlService {

    private final SurlRepository surlRepository;

    public long count() {
        return surlRepository.count();
    }

    @Transactional
    public Surl create(String url, String title) {
        Surl surl = Surl.builder()
                .url(url)
                .title(title)
                .build();

        surlRepository.save(surl);

        return surl;
    }

    public Optional<Surl> findById(long id) {
        return surlRepository.findById(id);
    }

    @Transactional
    public void modify(long id, String title) {
        Surl surl = surlRepository.findById(id).get();
        surl.setTitle(title);
    }
}
