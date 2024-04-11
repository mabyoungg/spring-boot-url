package org.example.springbooturl.domain.surl.surl.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbooturl.domain.member.member.service.MemberService;
import org.example.springbooturl.domain.surl.surl.entity.Surl;
import org.example.springbooturl.domain.surl.surl.service.SurlService;
import org.example.springbooturl.global.rq.Rq;
import org.example.springbooturl.global.rsData.RsData;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/surls")
@RequiredArgsConstructor
@Slf4j
public class ApiV1SurlController {
    private final SurlService surlService;
    private final MemberService memberService;
    private final Rq rq;

    public record SurlCreateReqBody(@NotBlank String url, String title) {
    }

    public record SurlCreateRespBody(String shortUrl) {
    }

    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public RsData<SurlCreateRespBody> create(
            @Valid @RequestBody SurlCreateReqBody reqBody
    ) {
        Surl surl = surlService.create(rq.getMember(), reqBody.url, reqBody.title);

        return RsData.of(new SurlCreateRespBody(surl.getShortUrl()));}

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
