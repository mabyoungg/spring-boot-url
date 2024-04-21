package org.example.springbooturl.domain.surl.surlDocument.repository;

import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.SearchRequest;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import com.meilisearch.sdk.model.SearchResult;
import com.meilisearch.sdk.model.Searchable;
import lombok.RequiredArgsConstructor;
import org.example.springbooturl.standard.util.ut.Ut;
import org.example.springbooturl.domain.surl.surlDocument.document.SurlDocument;
import org.example.springbooturl.global.app.AppConfig;
import org.example.springbooturl.global.meilisearch.MeilisearchConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SurlDocumentRepository {
    private final MeilisearchConfig meilisearchConfig;
    private Index index;

    private String getIndexName() {
        String indexName = "surl";

        if (AppConfig.isTest()) indexName += "Test";

        return indexName;
    }

    private Index getIndex() {
        if (index == null) index = meilisearchConfig.meilisearchClient().index(getIndexName());

        return index;
    }

    public void save(SurlDocument surlDocument) {
        getIndex().addDocuments(
                Ut.json.toString(surlDocument)
        );
    }

    public void clear() {
//        getIndex().addDocuments(
//                """
//                        {
//                            "id": 1
//                        }
//                        """
//        );
        getIndex().deleteAllDocuments();
        getIndex().resetSortableAttributesSettings();
        getIndex().updateSortableAttributesSettings(new String[]{"id"});
        getIndex().updateFilterableAttributesSettings(new String[]{"createTimeStamp"});
    }

    public List<SurlDocument> findByOrderByIdDesc() {
        // 검색 파라미터 설정
        SearchRequest searchRequest =
                new SearchRequest("")
                        .setSort(new String[]{"id:desc"});

        // 문서 검색
        Searchable searchable = getIndex().search(searchRequest);

        return
                searchable
                        .getHits()
                        .stream()
                        .map(
                                hit -> Ut.json.toObject(hit, SurlDocument.class)
                        )
                        .toList();

    }

    public Optional<SurlDocument> findById(long id) {
        try {
            SurlDocument document = getIndex().getDocument(String.valueOf(id), SurlDocument.class);
            return Optional.ofNullable(document);
        } catch (MeilisearchException ignored) {
        }

        return Optional.empty();
    }

    public Page<SurlDocument> findByKw(String kw, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        SearchRequest searchRequest =
                new SearchRequest(kw)
                        .setAttributesToSearchOn(new String[]{"title", "body", "url"})
                        .setLimit(pageable.getPageSize())
                        .setOffset((int) pageable.getOffset())
                        .setShowMatchesPosition(true)
                        .setAttributesToHighlight(new String[]{"title", "body", "url"});

        if (startDate != null && endDate != null) {
            searchRequest
                    .setFilter(new String[]{
                            "createTimeStamp >= %d AND createTimeStamp <= %d"
                                    .formatted(
                                    Ut.time.toTimeStamp(startDate),
                                    Ut.time.toTimeStamp(endDate)
                            )
                    });
        }

        SearchResult searchResult = (SearchResult) getIndex().search(searchRequest);

        List<SurlDocument> surlDocuments = searchResult
                .getHits()
                .stream()
                .map(
                        hit -> {
                            Map<String, Object> formatted = (Map<String, Object>) hit.get("_formatted");
                            return Ut.json.toObject(formatted, SurlDocument.class);
                        }
                )
                .toList();

        return new PageImpl<>(surlDocuments, pageable, searchResult.getEstimatedTotalHits());
    }
}
