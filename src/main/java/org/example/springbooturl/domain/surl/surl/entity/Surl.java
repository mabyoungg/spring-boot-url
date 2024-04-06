package org.example.springbooturl.domain.surl.surl.entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.example.springbooturl.global.jpa.entity.BaseTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Getter
@Setter
public class Surl extends BaseTime {
    private String url;
    private String title;
}
