package org.example.springbooturl.global.meilisearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import com.meilisearch.sdk.json.JacksonJsonHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MeilisearchConfig {
    private final ObjectMapper objectMapper;

    @Value("${custom.meilisearch.host}")
    private String meilisearchHost;

    @Value("${custom.meilisearch.apiKey}")
    private String meilisearchApiKey;

    @Bean
    public Client meilisearchClient() {
        return new Client(
                new Config(
                        meilisearchHost,
                        meilisearchApiKey,
                        new JacksonJsonHandler(objectMapper)
                )
        );
    }
}