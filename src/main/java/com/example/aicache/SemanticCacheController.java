package com.example.aicache;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SemanticCacheController {

    @Autowired
    private SemanticCacheService cacheService;

    @PostMapping("/ask")
    public List<String> ask(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        return cacheService.askQuestion(question);
    }

    @PostMapping("/enrich")
    public Map<String, String> enrich(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        String answer = request.get("answer");
        cacheService.enrich(question, answer);
        return Map.of("status", "ok");
    }
}

