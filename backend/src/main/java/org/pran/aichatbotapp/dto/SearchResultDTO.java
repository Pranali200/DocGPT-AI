package org.pran.aichatbotapp.dto;

import lombok.Setter;

public class SearchResultDTO {

    @Setter
    private Long chunkId;
    @Setter
    private String content;
    @Setter
    private Integer chunkIndex;
    @Setter
    private Double distance;

    public SearchResultDTO() {
    }

    public SearchResultDTO(
            Long chunkId,
            String content,
            Integer chunkIndex,
            Double distance) {

        this.chunkId = chunkId;
        this.content = content;
        this.chunkIndex = chunkIndex;
        this.distance = distance;
    }

    public Long getChunkId() {
        return chunkId;
    }

    public String getContent() {
        return content;
    }

    public Integer getChunkIndex() {
        return chunkIndex;
    }

    public Double getDistance() {
        return distance;
    }
}       
