package org.pran.aichatbotapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Getter
        @Setter
        @Column(nullable=false,unique = true)
        private String email;

        @Getter
        @Setter
        @Column(nullable = false)
        private String name;

        @Getter
        @Setter
        @Column(nullable = false)
        private String password;

        @Getter
        @Setter
        @Column(nullable = false)
        private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Document> documents = new ArrayList<>();

        public User(){

        }

        public User(String name, String email,String password){
            this.name = name;
            this.email= email;
            this.password= password;
            this.createdAt = LocalDateTime.now();
        }
        @PrePersist
        protected void onCreate(){
            if(createdAt==null){
                createdAt= LocalDateTime.now();
            }
        }

        public Long getId(){
            return id;
        }

        public void setId(Long id){
            this.id = id;
        }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void addDocument(Document document) {
        documents.add(document);
        document.setUser(this);
    }

    public void removeDocument(Document document) {
        documents.remove(document);
        document.setUser(null);
    }

    }


