package org.pran.aichatbotapp.controller;

import org.pran.aichatbotapp.dto.ChatRequest;

import org.pran.aichatbotapp.dto.ChatResponse;
import org.pran.aichatbotapp.service.RagService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final RagService ragService;

    public ChatController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(
            @RequestBody ChatRequest request) {

        ChatResponse response =
                ragService.ask(
                        request.getDocumentId(),
                        request.getSessionId(),
                        request.getQuestion()
                );

        return ResponseEntity.ok(response);
    }
}