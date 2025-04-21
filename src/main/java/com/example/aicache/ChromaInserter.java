package com.example.aicache;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import org.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.example.aicache.Config.embeddingModel;
import static com.example.aicache.Config.embeddingStore;


@Service
public class ChromaInserter {


    private RedisTemplate<String, String> redisTemplate;


    public ChromaInserter() {
    }

    /**
     * Add text.
     */
    public void addDocuments(String text) {
        TextSegment segment1 = TextSegment.from(text, new Metadata());
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        embeddingStore.add(embedding1, segment1);
    }

    /**
     * Add text with metadata.
     */
    public void addDocuments(String text, Metadata metadata) {
        TextSegment segment1 = TextSegment.from(text, metadata);
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        embeddingStore.add(embedding1, segment1);
    }


    public void addQuestionAnswer(String question, String answer) {
        // Store 'answer' inside metadata
        Metadata metadata = new Metadata();
        metadata.add("answer", answer);

        // Create a TextSegment based on the question
        TextSegment questionSegment = TextSegment.from(question, metadata);

        // Create embedding from the question
        Embedding embedding = embeddingModel.embed(questionSegment).content();

        // Add embedding and the associated question segment (with answer metadata)
        embeddingStore.add(embedding, questionSegment);
    }




}
