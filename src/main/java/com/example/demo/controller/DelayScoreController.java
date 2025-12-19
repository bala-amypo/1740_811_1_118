package com.example.demo.controller;

import com.example.demo.model.DelayScoreRecord;
import com.example.demo.service.DelayScoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delay-scores")
@Tag(name = "Delay Scores", description = "Delay score computation and management endpoints")
public class DelayScoreController {
    private final DelayScoreService service;

    public DelayScoreController(DelayScoreService service) {
        this.service = service;
    }

    @PostMapping("/compute/{poId}")
    @Operation(summary = "Compute delay score for a purchase order")
    public ResponseEntity<DelayScoreRecord> computeDelayScore(@PathVariable Long poId) {
        DelayScoreRecord score = service.computeDelayScore(poId);
        return ResponseEntity.status(HttpStatus.CREATED).body(score);
    }

    @GetMapping("/supplier/{supplierId}")
    @Operation(summary = "Get delay scores by supplier ID")
    public ResponseEntity<List<DelayScoreRecord>> getScoresBySupplier(@PathVariable Long supplierId) {
        List<DelayScoreRecord> scores = service.getScoresBySupplier(supplierId);
        return ResponseEntity.ok(scores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get delay score by ID")
    public ResponseEntity<DelayScoreRecord> getScoreById(@PathVariable Long id) {
        DelayScoreRecord score = service.getScoreById(id);
        return ResponseEntity.ok(score);
    }

    @GetMapping
    @Operation(summary = "Get all delay scores")
    public ResponseEntity<List<DelayScoreRecord>> getAllScores() {
        List<DelayScoreRecord> scores = service.getAllScores();
        return ResponseEntity.ok(scores);
    }
}

