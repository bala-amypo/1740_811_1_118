package com.example.demo.service;

import com.example.demo.model.DelayScoreRecord;
import java.util.List;

public interface DelayScoreService {
    DelayScoreRecord createDelayScore(DelayScoreRecord delayScore);
    List<DelayScoreRecord> getScoresBySupplier(Long supplierId);
    DelayScoreRecord getScoreById(Long id);
    List<DelayScoreRecord> getAllScores();
    DelayScoreRecord updateDelayScore(Long id, DelayScoreRecord delayScore);
    void deleteDelayScore(Long id);
}

