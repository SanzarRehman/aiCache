package com.example.aicache;



import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.example.aicache.Config.embeddingStore;

@Service
public class SemanticCacheService {


    private final ChromaSearcher chromaSearcher;

    private final ChromaInserter chromaInserter;


    private static final int TOP_K = 1;
    private static final double SIMILARITY_THRESHOLD = 0.85;

    public SemanticCacheService(ChromaSearcher chromaSearcher, ChromaInserter chromaInserter) {
        this.chromaSearcher = chromaSearcher;
        this.chromaInserter = chromaInserter;
    }

    public List<String> askQuestion(String question) {

        String answer = chromaSearcher.findAnswerByQuestion(question);
        if (answer.equals("Cache Miss: No similar question found.")) {

            return List.of(answer);
        } else {
            return List.of(answer);
        }

    }

    public void enrich(String question, String answer) {
        chromaInserter.addQuestionAnswer(question, answer);
    }

    public void addToEmbeddingStore(Embedding embedding, TextSegment segment) {
        embeddingStore.add(embedding, segment);
    }
}

