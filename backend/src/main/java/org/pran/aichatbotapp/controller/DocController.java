package org.pran.aichatbotapp.controller;

import org.pran.aichatbotapp.model.Document;
import org.pran.aichatbotapp.service.DocService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocController {

    private final DocService docService;

    public DocController(DocService docService) {
        this.docService = docService;
    }

    // ==============================
    // Upload PDF
    // ==============================

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDoc(
            @RequestParam("file") MultipartFile file,
            Authentication authentication)
            throws IOException {

        String email = authentication.getName();

        Document document =
                docService.uploadDocument(file, email);

        return ResponseEntity.ok(document);
    }

    // ==============================
    // Get all documents
    // ==============================

    @GetMapping
    public ResponseEntity<List<Document>>
    getAllDocuments(
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(
                docService.getAllDocuments(
                        userDetails.getUsername()
                )
        );
    }


    // ==============================
    // Get document by ID
    // ==============================

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                docService.getDocumentById(id, email)
        );
    }

    // ==============================
    // Delete document
    // ==============================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        docService.deleteDocument(id, email);

        return ResponseEntity.noContent()
                .build();
    }
    
}