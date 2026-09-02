package org.pran.aichatbotapp.controller;

import org.pran.aichatbotapp.dto.SearchResultDTO;
import org.pran.aichatbotapp.dto.VectorSearchRequest;
import org.pran.aichatbotapp.model.DocumentChunk;
import org.pran.aichatbotapp.service.VectorSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class VectorSearchController {

    private final VectorSearchService vectorSearchService;

    public VectorSearchController(
            VectorSearchService vectorSearchService) {

        this.vectorSearchService = vectorSearchService;
    }

    @PostMapping
    public ResponseEntity<List<SearchResultDTO>> search(
            @RequestBody VectorSearchRequest request) {

        int limit = request.getLimit() != null
                ? request.getLimit()
                : 5;

        List<SearchResultDTO> results =
                vectorSearchService
                        .search(
                                request.getDocumentId(),
                        request.getQuery(),
                        limit
                );

        return ResponseEntity.ok(results);
    }
}