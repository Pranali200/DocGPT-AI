package org.pran.aichatbotapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private String fileName;

    private Long fileSize;

    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    @Getter
    @Setter
    private User user;

    // ==========================================
    // Document → Chunks
    // ==========================================
    @JsonIgnore
    @OneToMany(
            mappedBy = "document",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DocumentChunk> chunks = new ArrayList<>();
    @JsonIgnore
    @OneToMany(
            mappedBy = "document",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ChatSession> chatSessions = new ArrayList<>();

    // ==========================================
    // Constructors
    // ==========================================

    public Document() {
    }

    public Document(
            Long id,
            String fileType,
            String fileName,
            Long fileSize,
            LocalDateTime uploadedAt) {

        this.id = id;
        this.fileType = fileType;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.uploadedAt = uploadedAt;
    }

    // ==========================================
    // Automatically set upload time
    // ==========================================

    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDateTime.now();
    }

    // ==========================================
    // ID
    // ==========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // ==========================================
    // File Type
    // ==========================================

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    // ==========================================
    // File Name
    // ==========================================

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // ==========================================
    // File Size
    // ==========================================

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    // ==========================================
    // Uploaded At
    // ==========================================

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    // ==========================================
    // Chunks
    // ==========================================

    public List<DocumentChunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<DocumentChunk> chunks) {
        this.chunks = chunks;
    }

    public void addChunk(DocumentChunk chunk) {
        chunks.add(chunk);
        chunk.setDocument(this);
    }

    public void removeChunk(DocumentChunk chunk) {
        chunks.remove(chunk);
        chunk.setDocument(null);
    }

    public List<ChatSession> getChatSessions() {
        return chatSessions;
    }

    public void setChatSessions(
            List<ChatSession> chatSessions) {

        this.chatSessions = chatSessions;
    }

    public void addChatSession(ChatSession session) {
        chatSessions.add(session);
        session.setDocument(this);
    }

    public void removeChatSession(ChatSession session) {
        chatSessions.remove(session);
        session.setDocument(null);
    }
}