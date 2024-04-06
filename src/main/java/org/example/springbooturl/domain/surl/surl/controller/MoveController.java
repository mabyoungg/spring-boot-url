package org.example.springbooturl.domain.surl.surl.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbooturl.domain.surl.surl.entity.Surl;
import org.example.springbooturl.domain.surl.surl.service.SurlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MoveController {
    private final SurlService surlService;

    @GetMapping("/{id:\\d+}")
    public String move(
            @PathVariable long id
    ) {
        Surl surl = surlService.findById(id).get();

        return "redirect:" + surl.getUrl();
    }
}
