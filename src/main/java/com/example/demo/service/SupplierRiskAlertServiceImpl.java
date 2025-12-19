package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.SupplierRiskAlert;
import com.example.demo.repository.SupplierRiskAlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierRiskAlertServiceImpl implements SupplierRiskAlertService {
    private final SupplierRiskAlertRepository repository;

    public SupplierRiskAlertServiceImpl(SupplierRiskAlertRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("null")
    @Override
    public SupplierRiskAlert createAlert(SupplierRiskAlert alert) {
        return repository.save(alert);
    }

    @Override
    public SupplierRiskAlert resolveAlert(Long id) {
        SupplierRiskAlert alert = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Risk alert not found"));
        alert.setResolved(true);
        return repository.save(alert);
    }

    @SuppressWarnings("null")
    @Override
    public SupplierRiskAlert getAlertById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Risk alert not found"));
    }

    @Override
    public List<SupplierRiskAlert> getAlertsBySupplier(Long supplierId) {
        return repository.findBySupplierId(supplierId);
    }

    @Override
    public List<SupplierRiskAlert> getAllAlerts() {
        return repository.findAll();
    }
}

