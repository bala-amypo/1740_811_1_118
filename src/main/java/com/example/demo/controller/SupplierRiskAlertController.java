package com.example.demo.controller;

import com.example.demo.model.SupplierRiskAlert;
import com.example.demo.service.SupplierRiskAlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risk-alerts")
public class SupplierRiskAlertController {
    
    private final SupplierRiskAlertService supplierRiskAlertService;
    
    public SupplierRiskAlertController(SupplierRiskAlertService supplierRiskAlertService) {
        this.supplierRiskAlertService = supplierRiskAlertService;
    }
    
    @PostMapping
    public ResponseEntity<SupplierRiskAlert> createAlert(@RequestBody SupplierRiskAlert alert) {
        SupplierRiskAlert created = supplierRiskAlertService.createAlert(alert);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/{id}/resolve")
    public ResponseEntity<SupplierRiskAlert> resolveAlert(@PathVariable Long id) {
        SupplierRiskAlert alert = supplierRiskAlertService.resolveAlert(id);
        return ResponseEntity.ok(alert);
    }
    
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<SupplierRiskAlert>> getAlertsBySupplier(@PathVariable Long supplierId) {
        List<SupplierRiskAlert> alerts = supplierRiskAlertService.getAlertsBySupplier(supplierId);
        return ResponseEntity.ok(alerts);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SupplierRiskAlert> getAlertById(@PathVariable Long id) {
        SupplierRiskAlert alert = supplierRiskAlertService.getAlertById(id);
        return ResponseEntity.ok(alert);
    }
    
    @GetMapping
    public ResponseEntity<List<SupplierRiskAlert>> getAllAlerts() {
        List<SupplierRiskAlert> alerts = supplierRiskAlertService.getAllAlerts();
        return ResponseEntity.ok(alerts);
    }
}

