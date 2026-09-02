package org.pran.aichatbotapp.controller;


import org.pran.aichatbotapp.model.ChatMessage;
import org.pran.aichatbotapp.model.ChatSession;
import org.pran.aichatbotapp.service.ChatMessageService;
import org.pran.aichatbotapp.service.ChatSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatHistoryController {

    private final ChatSessionService chatSessionService;
    private final ChatMessageService chatMessageService;

    public ChatHistoryController(
            ChatSessionService chatSessionService,
            ChatMessageService chatMessageService) {

        this.chatSessionService = chatSessionService;
        this.chatMessageService = chatMessageService;
    }

    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<ChatSession> getSession(
            @PathVariable Long sessionId) {

        return ResponseEntity.ok(
                chatSessionService.getSession(sessionId)
        );
    }

    @GetMapping("/sessions/{sessionId}/messages")
    public ResponseEntity<List<ChatMessage>> getMessages(
            @PathVariable Long sessionId) {

        return ResponseEntity.ok(
                chatMessageService.getMessages(sessionId)
        );
    }
}
