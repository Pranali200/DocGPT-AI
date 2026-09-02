package org.pran.aichatbotapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class DocumentResponse {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String fileName;
    @Getter
    @Setter
    private LocalDateTime uploadedAt;

    public DocumentResponse(){

    }
    public DocumentResponse(Long id,String fileName, LocalDateTime uploadedAt){
        this.id = id;
        this.fileName=fileName;
        this.uploadedAt = uploadedAt;
    }
}

