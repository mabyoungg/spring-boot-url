package org.example.springbooturl.domain.surl.surl.repository;

import org.example.springbooturl.domain.surl.surl.entity.Surl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurlRepository extends JpaRepository<Surl, Long> {
}
