package org.pran.aichatbotapp.service;

import org.pran.aichatbotapp.model.ChatSession;
import org.pran.aichatbotapp.model.Document;
import org.pran.aichatbotapp.repository.ChatSessionRepository;
import org.pran.aichatbotapp.repository.DocRepo;
import org.springframework.stereotype.Service;

@Service
public class ChatSessionService {

    private final ChatSessionRepository chatSessionRepository;

    private final DocRepo docRepo;

    public ChatSessionService(
            ChatSessionRepository chatSessionRepository,
            DocRepo docRepo) {

        this.chatSessionRepository =
                chatSessionRepository;

        this.docRepo = docRepo;
    }

    public ChatSession createSession(
            Long documentId) {

        Document document =
                docRepo.findById(documentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Document not found with id: "
                                                + documentId
                                )
                        );

        ChatSession session =
                new ChatSession(document);

        return chatSessionRepository.save(session);
    }

    public ChatSession getSession(
            Long sessionId) {

        return chatSessionRepository
                .findById(sessionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Chat session not found with id: "
                                        + sessionId
                        )
                );
    }
}