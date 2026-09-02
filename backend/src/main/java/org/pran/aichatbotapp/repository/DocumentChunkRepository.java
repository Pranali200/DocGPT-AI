package org.pran.aichatbotapp.repository;

import org.pran.aichatbotapp.model.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk,Long> {


    List<DocumentChunk> findByDocumentIdOrderByChunkIndex(Long documentId);

}
