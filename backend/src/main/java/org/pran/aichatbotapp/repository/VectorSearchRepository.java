package org.pran.aichatbotapp.repository;

import org.pran.aichatbotapp.dto.SearchResultDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VectorSearchRepository {

    private final JdbcTemplate jdbcTemplate;

    public VectorSearchRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SearchResultDTO> searchSimilarChunks(
            Long documentId,
            String pgVector,
            int limit) {

        String sql = """
                SELECT
                    dc.id,
                    dc.document_id,
                    dc.content,
                    dc.chunk_index,
                    1 - (dc.embedding <=> CAST(? AS vector)) AS similarity
                FROM document_chunks dc
                WHERE dc.document_id = ?
                ORDER BY dc.embedding <=> CAST(? AS vector)
                LIMIT ?
                """;

        return jdbcTemplate.query(
                sql,
                new Object[]{
                        pgVector,
                        documentId,
                        pgVector,
                        limit
                },
                (rs, rowNum) -> {

                    SearchResultDTO result =
                            new SearchResultDTO();

                    result.setChunkId(
                            rs.getLong("id")
                    );

                    result.setContent(
                            rs.getString("content")
                    );

                    result.setChunkIndex(
                            rs.getInt("chunk_index")
                    );

                    result.setDistance(
                            rs.getDouble("similarity")
                    );

                    return result;
                }
        );
    }
}