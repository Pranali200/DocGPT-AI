package org.pran.aichatbotapp.service;

import org.pran.aichatbotapp.model.ChatMessage;
import org.pran.aichatbotapp.model.ChatSession;
import org.pran.aichatbotapp.model.MessageRole;
import org.pran.aichatbotapp.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {

    private final ChatMessageRepository
            chatMessageRepository;

    public ChatMessageService(
            ChatMessageRepository chatMessageRepository) {

        this.chatMessageRepository =
                chatMessageRepository;
    }

    public ChatMessage saveMessage(
            ChatSession session,
            MessageRole role,
            String content) {

        ChatMessage message =
                new ChatMessage(
                        session,
                        role,
                        content
                );

        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getMessages(
            Long sessionId) {

        return chatMessageRepository
                .findBySessionIdOrderByCreatedAtAsc(
                        sessionId
                );
    }
}
