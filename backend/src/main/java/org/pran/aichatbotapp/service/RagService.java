package org.pran.aichatbotapp.service;

import org.pran.aichatbotapp.dto.ChatResponse;
import org.pran.aichatbotapp.dto.SearchResultDTO;
import org.pran.aichatbotapp.model.ChatMessage;
import org.pran.aichatbotapp.model.ChatSession;
import org.pran.aichatbotapp.model.MessageRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagService {

    private final VectorSearchService vectorSearchService;
    private final LlmService llmService;
    private final ChatSessionService chatSessionService;
    private final ChatMessageService chatMessageService;

    public RagService(
            VectorSearchService vectorSearchService,
            LlmService llmService,
            ChatSessionService chatSessionService,
            ChatMessageService chatMessageService) {

        this.vectorSearchService = vectorSearchService;
        this.llmService = llmService;
        this.chatSessionService = chatSessionService;
        this.chatMessageService = chatMessageService;
    }

    public ChatResponse ask(
            Long documentId,
            Long sessionId,
            String question) {

        // 1. Create new session OR get existing session
        ChatSession session;

        if (sessionId == null) {

            session = chatSessionService
                    .createSession(documentId);

        } else {

            session = chatSessionService
                    .getSession(sessionId);
        }

        // 2. Save user message
        chatMessageService.saveMessage(
                session,
                MessageRole.USER,
                question
        );

        // 3. Vector similarity search
        List<SearchResultDTO> results =
                vectorSearchService.search(
                        documentId,
                        question,
                        5
                );

        // 4. Build document context
        StringBuilder context =
                new StringBuilder();

        for (SearchResultDTO result : results) {

            context.append(
                    result.getContent()
            );

            context.append("\n\n");
        }

        // 5. Get previous conversation
        List<ChatMessage> history =
                chatMessageService.getMessages(
                        session.getId()
                );

        StringBuilder conversation =
                new StringBuilder();

        for (ChatMessage message : history) {

            conversation
                    .append(message.getRole())
                    .append(": ")
                    .append(message.getContent())
                    .append("\n");
        }

        // 6. Build RAG prompt
        String prompt = """
                You are an AI assistant answering
                questions about a user's uploaded document.

                Use the provided document context as your
                primary source of truth.

                If the answer cannot be found in the
                document context, say that the information
                is not available in the document.

                DOCUMENT CONTEXT:
                %s

                CONVERSATION HISTORY:
                %s

                CURRENT QUESTION:
                %s

                Answer clearly and accurately.
                """.formatted(
                context,
                conversation,
                question
        );

        // 7. Call Groq
        String answer =
                llmService.generateAnswer(prompt);

        // 8. Save assistant response
        chatMessageService.saveMessage(
                session,
                MessageRole.ASSISTANT,
                answer
        );

        // 9. Return response
        return new ChatResponse(
                session.getId(),
                answer
        );
    }
}