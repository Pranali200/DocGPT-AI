package org.pran.aichatbotapp.repository;

import org.pran.aichatbotapp.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DocRepo extends JpaRepository<Document,Long> {
    List<Document> findByUserEmail(String email);

    List<Document> findByUserId(Long userId);

    Optional<Document> findByIdAndUserEmail(
            Long id,
            String email
    );
}
