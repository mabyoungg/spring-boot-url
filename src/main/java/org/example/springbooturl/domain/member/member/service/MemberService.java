package org.example.springbooturl.domain.member.member.service;

import lombok.RequiredArgsConstructor;
import org.example.springbooturl.domain.member.member.entity.Member;
import org.example.springbooturl.domain.member.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member create(String username, String password, String nickname) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .build();

        return memberRepository.save(member);
    }

    public Optional<Member> findById(long id) {
        return memberRepository.findById(id);
    }
}
