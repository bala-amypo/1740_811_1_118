package com.example.demo.service.impl;

import com.example.demo.model.SupplierRiskAlert;
import com.example.demo.repository.SupplierRiskAlertRepository;
import com.example.demo.service.SupplierRiskAlertService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierRiskAlertServiceImpl implements SupplierRiskAlertService {
    
    private final SupplierRiskAlertRepository supplierRiskAlertRepository;
    
    public SupplierRiskAlertServiceImpl(SupplierRiskAlertRepository supplierRiskAlertRepository) {
        this.supplierRiskAlertRepository = supplierRiskAlertRepository;
    }
    
    @Override
    public SupplierRiskAlert createAlert(SupplierRiskAlert alert) {
        return supplierRiskAlertRepository.save(alert);
    }
    
    @Override
    public SupplierRiskAlert resolveAlert(Long id) {
        SupplierRiskAlert alert = getAlertById(id);
        alert.setResolved(true);
        return supplierRiskAlertRepository.save(alert);
    }
    
    @Override
    public SupplierRiskAlert getAlertById(Long id) {
        return supplierRiskAlertRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alert not found with id: " + id));
    }
    
    @Override
    public List<SupplierRiskAlert> getAlertsBySupplier(Long supplierId) {
        return supplierRiskAlertRepository.findBySupplierId(supplierId);
    }
    
    @Override
    public List<SupplierRiskAlert> getAllAlerts() {
        return supplierRiskAlertRepository.findAll();
    }
}

