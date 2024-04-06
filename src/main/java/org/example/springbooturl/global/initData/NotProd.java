package org.example.springbooturl.global.initData;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbooturl.domain.surl.surl.service.SurlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class NotProd {
    @Autowired
    @Lazy
    private NotProd self;
    private final SurlService surlService;

    @Bean
    public ApplicationRunner initNotProd() {
        return args -> {
            if ( surlService.count() > 0 ) return;

            self.work1();
        };
    }

    @Transactional
    public void work1() {
        surlService.save("https://www.naver.com", "네이버");
        surlService.save("https://www.google.com", "구글");
    }
}
