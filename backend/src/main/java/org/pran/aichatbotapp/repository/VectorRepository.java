package org.pran.aichatbotapp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VectorRepository {

    private final JdbcTemplate jdbcTemplate;

    public VectorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveEmbedding(
            Long chunkId,
            String embedding) {

        String sql = """
                UPDATE document_chunks
                SET embedding = ?::vector
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                embedding,
                chunkId
        );
    }
}