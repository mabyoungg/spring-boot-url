package org.example.springbooturl.domain.member.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.*;
import org.example.springbooturl.global.jpa.entity.BaseTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
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

    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthoritiesAsStringList()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Transient
    public List<String> getAuthoritiesAsStringList() {
        List<String> authorities = new ArrayList<>();

        if (isAdmin())
            authorities.add("ROLE_ADMIN");

        return authorities;
    }

    private boolean isAdmin() {
        return username.equals("admin");
    }

    public String getName() {
        return nickname;
    }
}
