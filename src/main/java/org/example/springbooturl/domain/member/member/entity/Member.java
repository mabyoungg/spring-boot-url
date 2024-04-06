package org.example.springbooturl.domain.member.member.entity;

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
public class Member extends BaseTime {
    private String username;
    private String password;
    private String nickname;
}
