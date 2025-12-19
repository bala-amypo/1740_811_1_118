package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.DelayScoreRecordRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.repository.PurchaseOrderRecordRepository;
import com.example.demo.repository.SupplierProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DelayScoreServiceImpl implements DelayScoreService {
    private final DelayScoreRecordRepository delayScoreRepository;
    private final PurchaseOrderRecordRepository purchaseOrderRepository;
    private final DeliveryRecordRepository deliveryRepository;
    private final SupplierProfileRepository supplierRepository;
    private final SupplierRiskAlertService riskAlertService;

    public DelayScoreServiceImpl(DelayScoreRecordRepository delayScoreRepository,
                                PurchaseOrderRecordRepository purchaseOrderRepository,
                                DeliveryRecordRepository deliveryRepository,
                                SupplierProfileRepository supplierRepository,
                                SupplierRiskAlertService riskAlertService) {
        this.delayScoreRepository = delayScoreRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.deliveryRepository = deliveryRepository;
        this.supplierRepository = supplierRepository;
        this.riskAlertService = riskAlertService;
    }

    @SuppressWarnings("null")
    @Override
    @Transactional
    public DelayScoreRecord computeDelayScore(Long poId) {
        // Fetch PO
        PurchaseOrderRecord po = purchaseOrderRepository.findById(poId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found"));

        // Fetch deliveries
        List<DeliveryRecord> deliveries = deliveryRepository.findByPoId(poId);
        if (deliveries.isEmpty()) {
            throw new BadRequestException("No deliveries");
        }

        // Validate supplier is active
        SupplierProfile supplier = supplierRepository.findById(po.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
        if (!supplier.getActive()) {
            throw new BadRequestException("Supplier must be active");
        }

        // Calculate delay using the earliest delivery date
        DeliveryRecord earliestDelivery = deliveries.stream()
                .min((d1, d2) -> d1.getActualDeliveryDate().compareTo(d2.getActualDeliveryDate()))
                .orElseThrow();

        long delayDays = ChronoUnit.DAYS.between(po.getPromisedDeliveryDate(), earliestDelivery.getActualDeliveryDate());

        // Classify severity
        DelaySeverity severity;
        Double score;
        if (delayDays <= 0) {
            severity = DelaySeverity.ON_TIME;
            score = 0.0;
        } else if (delayDays <= 3) {
            severity = DelaySeverity.MINOR;
            score = 1.0;
        } else if (delayDays <= 7) {
            severity = DelaySeverity.MODERATE;
            score = 2.5;
        } else {
            severity = DelaySeverity.SEVERE;
            score = 5.0;
        }

        // Check if score already exists for this PO
        DelayScoreRecord existingScore = delayScoreRepository.findByPoId(poId).orElse(null);
        DelayScoreRecord delayScore;
        if (existingScore != null) {
            existingScore.setDelayDays((int) delayDays);
            existingScore.setDelaySeverity(severity);
            existingScore.setScore(score);
            delayScore = delayScoreRepository.save(existingScore);
        } else {
            delayScore = new DelayScoreRecord(po.getSupplierId(), poId, (int) delayDays, severity, score);
            delayScore = delayScoreRepository.save(delayScore);
        }

        // Update risk alerts
        updateRiskAlerts(po.getSupplierId());

        return delayScore;
    }

    private void updateRiskAlerts(Long supplierId) {
        List<DelayScoreRecord> scores = delayScoreRepository.findBySupplierId(supplierId);
        if (scores.isEmpty()) {
            return;
        }

        double avgScore = scores.stream()
                .mapToDouble(DelayScoreRecord::getScore)
                .average()
                .orElse(0.0);

        AlertLevel alertLevel;
        String message;
        if (avgScore < 1.5) {
            alertLevel = AlertLevel.LOW;
            message = "Supplier has low risk with average delay score: " + String.format("%.2f", avgScore);
        } else if (avgScore <= 3.0) {
            alertLevel = AlertLevel.MEDIUM;
            message = "Supplier has medium risk with average delay score: " + String.format("%.2f", avgScore);
        } else {
            alertLevel = AlertLevel.HIGH;
            message = "Supplier has high risk with average delay score: " + String.format("%.2f", avgScore);
        }

        // Check if alert already exists
        List<SupplierRiskAlert> existingAlerts = riskAlertService.getAlertsBySupplier(supplierId);
        SupplierRiskAlert unresolvedAlert = existingAlerts.stream()
                .filter(a -> !a.getResolved())
                .findFirst()
                .orElse(null);

        if (unresolvedAlert != null) {
            unresolvedAlert.setAlertLevel(alertLevel);
            unresolvedAlert.setMessage(message);
            riskAlertService.createAlert(unresolvedAlert);
        } else {
            SupplierRiskAlert alert = new SupplierRiskAlert(supplierId, alertLevel, message);
            riskAlertService.createAlert(alert);
        }
    }

    @Override
    public List<DelayScoreRecord> getScoresBySupplier(Long supplierId) {
        return delayScoreRepository.findBySupplierId(supplierId);
    }

    @Override
    public DelayScoreRecord getScoreById(Long id) {
        return delayScoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delay score not found"));
    }

    @Override
    public List<DelayScoreRecord> getAllScores() {
        return delayScoreRepository.findAll();
    }
}

