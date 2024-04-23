package org.example.springbooturl.domain.surl.surlDocument.service;

import lombok.RequiredArgsConstructor;
import org.example.springbooturl.domain.surl.surl.dto.SurlDto;
import org.example.springbooturl.domain.surl.surlDocument.document.SurlDocument;
import org.example.springbooturl.domain.surl.surlDocument.repository.SurlDocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SurlDocumentService {
    private final SurlDocumentRepository surlDocumentRepository;

    public void add(SurlDto surlDto) {
        SurlDocument surlDocument = new SurlDocument(surlDto);

        surlDocumentRepository.save(surlDocument);
    }

    public void clear() {
        surlDocumentRepository.clear();
    }

    public List<SurlDocument> findAll() {
        return surlDocumentRepository.findByOrderByIdDesc();
    }

    public Optional<SurlDocument> findById(long id) {
        return surlDocumentRepository.findById(id);
    }

    public Page<SurlDocument> findByKw(String kw, Pageable pageable) {
        return findByKw(kw, null, null, pageable);
    }

    public Page<SurlDocument> findByKw(String kw, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return surlDocumentRepository.findByKw(kw, startDate, endDate, pageable);
    }

    public void modify(SurlDto surlDto) {
        SurlDocument surlDocument = new SurlDocument(surlDto);

        surlDocumentRepository.update(surlDocument);
    }

    public void delete(SurlDto surlDto) {
        surlDocumentRepository.deleteById(surlDto.getId());
    }
}
