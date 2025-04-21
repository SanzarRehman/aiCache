package com.example.aicache;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.aicache.Config.embeddingModel;
import static com.example.aicache.Config.embeddingStore;


@Service
public class ChromaSearcher {


    public ChromaSearcher() {
    }
    public List<String> search(String query,
                                                           int maxResults) {
        Embedding queryEmbedding = embeddingModel.embed(query).content();
        return embeddingStore.findRelevant(queryEmbedding, maxResults).stream().map(s ->s.embedded().text()).toList();
    }

    public String findAnswerByQuestion(String userQuestion) {
        TextSegment querySegment = TextSegment.from(userQuestion);
        Embedding queryEmbedding = embeddingModel.embed(querySegment).content();

        List<EmbeddingMatch<TextSegment>> matches = embeddingStore.findRelevant(queryEmbedding, 1);

        if (!matches.isEmpty()) {
            EmbeddingMatch<TextSegment> bestMatch = matches.get(0);

            // If you want threshold checking
            if (bestMatch.score() > 0.8) {
                Metadata metadata = bestMatch.embedded().metadata();
                return metadata.get("answer");
            }
        }
        return "Cache Miss: No similar question found.";
    }

}
