package org.example.springbooturl.domain.surl.surl.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbooturl.domain.surl.surl.dto.SurlDto;
import org.example.springbooturl.domain.surl.surl.entity.Surl;
import org.example.springbooturl.domain.surl.surl.service.SurlService;
import org.example.springbooturl.domain.surl.surlDocument.document.SurlDocument;
import org.example.springbooturl.domain.surl.surlDocument.service.SurlDocumentService;
import org.example.springbooturl.global.app.AppConfig;
import org.example.springbooturl.global.rq.Rq;
import org.example.springbooturl.global.rsData.RsData;
import org.example.springbooturl.standard.base.KwTypeV1;
import org.example.springbooturl.standard.base.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/surls")
@RequiredArgsConstructor
@Slf4j
public class ApiV1SurlController {
    private final SurlService surlService;
    private final SurlDocumentService surlDocumentService;
    private final Rq rq;

    public record GetSurlsResponseBody(@NonNull PageDto<SurlDto> itemPage) {
    }

    @GetMapping("")
    public RsData<GetSurlsResponseBody> getSurls(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String kw,
            @RequestParam(defaultValue = "ALL") KwTypeV1 kwType
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("rating"));
        Pageable pageable = PageRequest.of(page - 1, AppConfig.getBasePageSize(), Sort.by(sorts));
        Page<SurlDocument> itemPage = surlDocumentService.findByKw(kw, pageable);

        Page<SurlDto> _itemPage = itemPage.map(
                surlDocument -> new SurlDto(surlDocument)
        );

        return RsData.of(
                new GetSurlsResponseBody(
                        new PageDto<>(_itemPage)
                )
        );
    }

    public record SurlCreateReqBody(@NotBlank String url, String title) {
    }

    public record SurlCreateRespBody(String shortUrl, String title) {
    }

    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public RsData<SurlCreateRespBody> create(
            @Valid @RequestBody SurlCreateReqBody reqBody
    ) {
        Surl surl = surlService.create(rq.getMember(), reqBody.url, reqBody.title);

        return RsData.of(new SurlCreateRespBody(surl.getShortUrl(), surl.getTitle()));}

    @Data
    public static class SurlModifyReqBody {
        @NotBlank
        public String title;
    }

    @PutMapping("/{id}")
    public void modify(
            @PathVariable long id,
            @Valid @RequestBody SurlModifyReqBody reqBody
    ) {
        surlService.modify(id, reqBody.title);
    }
}
