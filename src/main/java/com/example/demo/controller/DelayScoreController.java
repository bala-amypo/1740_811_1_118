package com.example.demo.controller;

import com.example.demo.model.DelayScoreRecord;
import com.example.demo.service.DelayScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delay-scores")
public class DelayScoreController {
    
    private final DelayScoreService delayScoreService;
    
    public DelayScoreController(DelayScoreService delayScoreService) {
        this.delayScoreService = delayScoreService;
    }
    
    @PostMapping
    public ResponseEntity<DelayScoreRecord> createDelayScore(@RequestBody DelayScoreRecord delayScore) {
        DelayScoreRecord created = delayScoreService.createDelayScore(delayScore);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DelayScoreRecord> updateDelayScore(
            @PathVariable Long id, 
            @RequestBody DelayScoreRecord delayScore) {
        DelayScoreRecord updated = delayScoreService.updateDelayScore(id, delayScore);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelayScore(@PathVariable Long id) {
        delayScoreService.deleteDelayScore(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<DelayScoreRecord>> getScoresBySupplier(@PathVariable Long supplierId) {
        List<DelayScoreRecord> scores = delayScoreService.getScoresBySupplier(supplierId);
        return ResponseEntity.ok(scores);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DelayScoreRecord> getScoreById(@PathVariable Long id) {
        DelayScoreRecord score = delayScoreService.getScoreById(id);
        return ResponseEntity.ok(score);
    }
    
    @GetMapping
    public ResponseEntity<List<DelayScoreRecord>> getAllScores() {
        List<DelayScoreRecord> scores = delayScoreService.getAllScores();
        return ResponseEntity.ok(scores);
    }
}

