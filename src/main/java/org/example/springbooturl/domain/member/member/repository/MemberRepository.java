package org.example.springbooturl.domain.member.member.repository;

import org.example.springbooturl.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
