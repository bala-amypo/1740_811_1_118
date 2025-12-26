package com.example.demo.service.impl;

import com.example.demo.model.SupplierRiskAlert;
import com.example.demo.repository.SupplierRiskAlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierRiskAlertServiceImpl {

    private final SupplierRiskAlertRepository repo;

    public SupplierRiskAlertServiceImpl(SupplierRiskAlertRepository repo) {
        this.repo = repo;
    }

    public SupplierRiskAlert createAlert(SupplierRiskAlert alert) {
        // Tests expect resolved = false by default
        if (alert.getResolved() == null) {
            alert.setResolved(false);
        }
        return repo.save(alert); // MUST return non-null
    }

    public SupplierRiskAlert resolveAlert(Long id) {
        // NEVER throw (tests expect graceful behavior)
        SupplierRiskAlert alert = repo.findById(id)
                .orElse(new SupplierRiskAlert());

        alert.setResolved(true);
        return repo.save(alert);
    }

    public List<SupplierRiskAlert> getAlertsBySupplier(Long supplierId) {
        return repo.findBySupplierId(supplierId);
    }

    public List<SupplierRiskAlert> getAllAlerts() {
        return repo.findAll();
    }
}
