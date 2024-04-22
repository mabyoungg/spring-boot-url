package org.example.springbooturl.domain.surl.surl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springbooturl.domain.surl.surl.entity.Surl;
import org.example.springbooturl.domain.surl.surlDocument.document.SurlDocument;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurlDto {
    @NonNull
    private long id;
    @NonNull
    private LocalDateTime createDate;
    @NonNull
    private LocalDateTime modifyDate;
    @NonNull
    private long authorId;
    @NonNull
    private String url;
    @NonNull
    private String title;
    @NonNull
    private String body;

    public SurlDto(Surl surl) {
        this.id = surl.getId();
        this.createDate = surl.getCreateDate();
        this.modifyDate = surl.getModifyDate();
        this.authorId = surl.getAuthor().getId();
        this.url = surl.getUrl();
        this.title = surl.getTitle();
        this.body = surl.getBody();
    }

    public SurlDto(SurlDocument surlDocument) {
        this.id = surlDocument.getId();
        this.createDate = surlDocument.getCreateDate();
        this.modifyDate = surlDocument.getModifyDate();
        this.authorId = surlDocument.getAuthorId();
        this.url = surlDocument.getUrl();
        this.title = surlDocument.getTitle();
        this.body = surlDocument.getBody();
    }
}
