package com.example.demo.service.impl;

import com.example.demo.model.DelayScoreRecord;
import com.example.demo.repository.DelayScoreRecordRepository;
import com.example.demo.service.DelayScoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelayScoreServiceImpl implements DelayScoreService {
    
    private final DelayScoreRecordRepository delayScoreRecordRepository;
    
    public DelayScoreServiceImpl(DelayScoreRecordRepository delayScoreRecordRepository) {
        this.delayScoreRecordRepository = delayScoreRecordRepository;
    }
    
    @Override
    public DelayScoreRecord createDelayScore(DelayScoreRecord delayScore) {
        return delayScoreRecordRepository.save(delayScore);
    }
    
    @Override
    public List<DelayScoreRecord> getScoresBySupplier(Long supplierId) {
        return delayScoreRecordRepository.findBySupplierId(supplierId);
    }
    
    @Override
    public DelayScoreRecord getScoreById(Long id) {
        return delayScoreRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Delay score not found with id: " + id));
    }
    
    @Override
    public List<DelayScoreRecord> getAllScores() {
        return delayScoreRecordRepository.findAll();
    }
    
    @Override
    public DelayScoreRecord updateDelayScore(Long id, DelayScoreRecord delayScore) {
        DelayScoreRecord existing = getScoreById(id);
        delayScore.setId(existing.getId());
        return delayScoreRecordRepository.save(delayScore);
    }
    
    @Override
    public void deleteDelayScore(Long id) {
        DelayScoreRecord existing = getScoreById(id);
        delayScoreRecordRepository.delete(existing);
    }
}

