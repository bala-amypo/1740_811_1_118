package com.example.demo.controller;

import com.example.demo.model.SupplierRiskAlert;
import com.example.demo.service.SupplierRiskAlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risk-alerts")
@Tag(name = "Risk Alerts", description = "Supplier risk alert management endpoints")
public class SupplierRiskAlertController {
    private final SupplierRiskAlertService service;

    public SupplierRiskAlertController(SupplierRiskAlertService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a risk alert")
    public ResponseEntity<SupplierRiskAlert> createAlert(@RequestBody SupplierRiskAlert alert) {
        SupplierRiskAlert created = service.createAlert(alert);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}/resolve")
    @Operation(summary = "Resolve a risk alert")
    public ResponseEntity<SupplierRiskAlert> resolveAlert(@PathVariable Long id) {
        SupplierRiskAlert resolved = service.resolveAlert(id);
        return ResponseEntity.ok(resolved);
    }

    @GetMapping("/supplier/{supplierId}")
    @Operation(summary = "Get risk alerts by supplier ID")
    public ResponseEntity<List<SupplierRiskAlert>> getAlertsBySupplier(@PathVariable Long supplierId) {
        List<SupplierRiskAlert> alerts = service.getAlertsBySupplier(supplierId);
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get risk alert by ID")
    public ResponseEntity<SupplierRiskAlert> getAlertById(@PathVariable Long id) {
        SupplierRiskAlert alert = service.getAlertById(id);
        return ResponseEntity.ok(alert);
    }

    @GetMapping
    @Operation(summary = "Get all risk alerts")
    public ResponseEntity<List<SupplierRiskAlert>> getAllAlerts() {
        List<SupplierRiskAlert> alerts = service.getAllAlerts();
        return ResponseEntity.ok(alerts);
    }
}

