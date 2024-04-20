package org.example.springbooturl.global.security;

import lombok.RequiredArgsConstructor;
import org.example.springbooturl.domain.member.member.entity.Member;
import org.example.springbooturl.domain.member.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new SecurityUser(
                member.getId(),
                member.getUsername(),
                member.getPassword(),
                member.getAuthorities()
        );
    }
}
