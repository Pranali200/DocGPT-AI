package org.pran.aichatbotapp.service;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class EmbeddingService {

    private final RestClient restClient;

    public EmbeddingService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("http://localhost:8001")
                .build();
    }

    public List<Double> generateEmbedding(String text) {

        EmbeddingRequest request =
                new EmbeddingRequest(text);

        EmbeddingResponse response =
                restClient.post()
                        .uri("/embed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(request)
                        .retrieve()
                        .body(EmbeddingResponse.class);

        if (response == null || response.embedding() == null) {
            throw new RuntimeException(
                    "Failed to generate embedding"
            );
        }

        return response.embedding();
    }

    public record EmbeddingRequest(String text) {
    }

    public record EmbeddingResponse(
            List<Double> embedding
    ) {
    }
}