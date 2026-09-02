package org.pran.aichatbotapp.service;

import org.pran.aichatbotapp.model.Document;
import org.pran.aichatbotapp.model.DocumentChunk;
import org.pran.aichatbotapp.model.User;
import org.pran.aichatbotapp.repository.DocRepo;
import org.pran.aichatbotapp.repository.DocumentChunkRepository;
import org.pran.aichatbotapp.repository.UserRepository;
import org.pran.aichatbotapp.repository.VectorRepository;
import org.pran.aichatbotapp.util.VectorUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class DocService {

    private final DocRepo documentRepository;
    private final DocumentChunkRepository documentChunkRepository;
    private final VectorRepository vectorRepository;
    private final PdfService pdfService;
    private final TextChunkingService textChunkingService;
    private final EmbeddingService embeddingService;
    private final UserRepository userRepository;

    private final Path uploadDirectory =
            Paths.get("uploads");

    public DocService(
            DocRepo documentRepository,
            DocumentChunkRepository documentChunkRepository,
            VectorRepository vectorRepository,
            PdfService pdfService,
            TextChunkingService textChunkingService,
            EmbeddingService embeddingService,
            UserRepository userRepository) {

        this.documentRepository = documentRepository;
        this.documentChunkRepository = documentChunkRepository;
        this.vectorRepository = vectorRepository;
        this.pdfService = pdfService;
        this.textChunkingService = textChunkingService;
        this.embeddingService = embeddingService;
        this.userRepository = userRepository;
    }


    // =========================================================
    // UPLOAD DOCUMENT
    // =========================================================

    public Document uploadDocument(
            MultipartFile file,
            String email) throws IOException {

        // 1. Find logged-in user
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );


        // 2. Validate file
        if (file.isEmpty()) {

            throw new IllegalArgumentException(
                    "File cannot be empty"
            );
        }


        String fileName =
                file.getOriginalFilename();


        if (fileName == null ||
                !fileName.toLowerCase().endsWith(".pdf")) {

            throw new IllegalArgumentException(
                    "Only PDF files are allowed"
            );
        }


        // 3. Create uploads directory
        if (!Files.exists(uploadDirectory)) {

            Files.createDirectories(
                    uploadDirectory
            );
        }


        // 4. Save physical PDF
        Path filePath =
                uploadDirectory.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );


        // =====================================================
        // 5. Extract text
        // =====================================================

        String extractedText =
                pdfService.extractText(file);


        // =====================================================
        // 6. Create Document
        // =====================================================

        Document document =
                new Document();

        document.setFileName(fileName);

        document.setFileType(
                "application/pdf"
        );

        document.setFileSize(
                file.getSize()
        );

        // IMPORTANT:
        // Associate document with logged-in user
        document.setUser(user);


        Document savedDocument =
                documentRepository.save(document);


        // =====================================================
        // 7. Split text into chunks
        // =====================================================

        List<String> chunks =
                textChunkingService.splitText(
                        extractedText
                );


        // =====================================================
        // 8. Generate embeddings
        // =====================================================

        for (int i = 0;
             i < chunks.size();
             i++) {

            String chunkText =
                    chunks.get(i);


            DocumentChunk documentChunk =
                    new DocumentChunk(
                            savedDocument,
                            chunkText,
                            i
                    );


            DocumentChunk savedChunk =
                    documentChunkRepository.save(
                            documentChunk
                    );


            // Generate embedding
            List<Double> embedding =
                    embeddingService.generateEmbedding(
                            chunkText
                    );


            // Convert to pgvector format
            String pgVector =
                    VectorUtils.toPgVector(
                            embedding
                    );


            // Save vector
            vectorRepository.saveEmbedding(
                    savedChunk.getId(),
                    pgVector
            );


            System.out.println(
                    "Processed chunk: " + i
            );
        }


        System.out.println(
                "Total chunks created: "
                        + chunks.size()
        );


        return savedDocument;
    }


    // =========================================================
    // GET CURRENT USER'S DOCUMENTS
    // =========================================================

    public List<Document> getAllDocuments(String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return documentRepository.findByUserId(user.getId());
    }


    // =========================================================
    // GET DOCUMENT BY ID
    // ONLY IF IT BELONGS TO CURRENT USER
    // =========================================================

    public Document getDocumentById(
            Long id,
            String email) {

        return documentRepository
                .findByIdAndUserEmail(id, email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Document not found"
                        )
                );
    }


    // =========================================================
    // DELETE DOCUMENT
    // ONLY IF IT BELONGS TO CURRENT USER
    // =========================================================

    public void deleteDocument(
            Long id,
            String email) {

        Document document =
                documentRepository
                        .findByIdAndUserEmail(
                                id,
                                email
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Document not found"
                                )
                        );


        documentRepository.delete(document);
    }
}