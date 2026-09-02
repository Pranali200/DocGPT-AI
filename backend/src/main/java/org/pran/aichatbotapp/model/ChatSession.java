package org.pran.aichatbotapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="chat_sessions")
public class ChatSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    @JsonIgnore
    private Document document;

    @OneToMany(
            mappedBy = "session",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

   @OrderBy("createdAt ASC")
   private List<ChatMessage> messages = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public ChatSession(){
    }

    public ChatSession(Document document){
        this.document = document;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Document getDocument(){
        return document;
    }

    public void setDocument(Document document) {
   this.document = document;
   }
 public List<ChatMessage> getMessages(){
        return messages;
 }

 public void setMessages(List<ChatMessage> messages){
        this.messages = messages;
 }

 public void addMessage(ChatMessage message){
     messages.add(message);
     message.setSession(this);
 }

 public void removeMessage(ChatMessage message){
        messages.remove(message);
        message.setSession(null);
 }

 public LocalDateTime getCreatedAt(){
        return createdAt;
 }

 public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
 }
}


