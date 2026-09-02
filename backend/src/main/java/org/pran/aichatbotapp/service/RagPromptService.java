package org.pran.aichatbotapp.service;
import org.pran.aichatbotapp.dto.SearchResultDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagPromptService {

    public String buildPrompt(
            String question,
            List<SearchResultDTO> results) {

        StringBuilder context =
                new StringBuilder();

        for (SearchResultDTO result : results) {

            context.append(
                    "\n--- DOCUMENT CHUNK ---\n"
            );

            context.append(
                    result.getContent()
            );

            context.append(
                    "\n--- END CHUNK ---\n"
            );
        }

        return """
                You are a helpful AI assistant.

                Answer the user's question using ONLY
                the information provided in the document
                context below.

                If the answer cannot be found in the
                provided context, say:

                "I couldn't find the answer in the uploaded document."

                Do not make up information.

                DOCUMENT CONTEXT:
                %s

                USER QUESTION:
                %s

                ANSWER:
                """.formatted(
                context,
                question
        );
    }
}
