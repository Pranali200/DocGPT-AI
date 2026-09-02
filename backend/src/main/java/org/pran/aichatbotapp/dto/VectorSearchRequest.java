package org.pran.aichatbotapp.dto;


import lombok.Getter;
import lombok.Setter;

public class VectorSearchRequest {

    @Getter
    @Setter
    private Long documentId;
    private String query;

    private Integer limit;

    public VectorSearchRequest() {
    }


    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}