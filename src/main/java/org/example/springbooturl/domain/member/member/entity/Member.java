package org.example.springbooturl.domain.member.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.example.springbooturl.global.jpa.entity.BaseTime;

import java.util.List;

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
    @Column(unique = true)
    private String refreshToken;
    private String nickname;

    public String getName() {
        return nickname;
    }

    public List<String> getAuthoritiesAsStringList() {
        if (isAdmin()) List.of("ROLE_ADMIN", "ROLE_MEMBER");

        return List.of("ROLE_MEMBER");
    }

    private boolean isAdmin() {
        return username.equals("admin");
    }
}
