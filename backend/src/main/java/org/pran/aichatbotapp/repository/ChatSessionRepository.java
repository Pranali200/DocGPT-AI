package org.pran.aichatbotapp.repository;

import org.pran.aichatbotapp.model.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatSessionRepository extends JpaRepository<ChatSession,Long> {

    List<ChatSession> findByDocumentIdOrderByCreatedAtDesc(Long documentId);

}
