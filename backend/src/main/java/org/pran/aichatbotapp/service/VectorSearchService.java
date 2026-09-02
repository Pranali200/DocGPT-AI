package org.pran.aichatbotapp.service;

import org.pran.aichatbotapp.dto.SearchResultDTO;
import org.pran.aichatbotapp.model.DocumentChunk;
import org.pran.aichatbotapp.repository.VectorSearchRepository;
import org.pran.aichatbotapp.util.VectorUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.List;

@Service
public class VectorSearchService {

    private final EmbeddingService embeddingService;
    private final VectorSearchRepository vectorSearchRepository;

    public VectorSearchService(
            EmbeddingService embeddingService,
            VectorSearchRepository vectorSearchRepository) {

        this.embeddingService = embeddingService;
        this.vectorSearchRepository =
                vectorSearchRepository;
    }

    public List<SearchResultDTO> search(
            Long documentId,
            String query,
            int limit) {

        if(documentId==null)throw new IllegalArgumentException("Document ID cannot be null");

        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException(
                    "Query cannot be empty"
            );
        }

        List<Double> queryEmbedding =
                embeddingService.generateEmbedding(query);

        String pgVector =
                VectorUtils.toPgVector(queryEmbedding);

        return vectorSearchRepository.searchSimilarChunks(
                documentId,
                pgVector,
                limit
        );
    }
}