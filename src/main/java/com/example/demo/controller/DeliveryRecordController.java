package com.example.demo.controller;

import com.example.demo.model.DeliveryRecord;
import com.example.demo.service.DeliveryRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@Tag(name = "Delivery Records", description = "Delivery record management endpoints")
public class DeliveryRecordController {
    private final DeliveryRecordService service;

    public DeliveryRecordController(DeliveryRecordService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Record a delivery")
    public ResponseEntity<DeliveryRecord> recordDelivery(@RequestBody DeliveryRecord delivery) {
        DeliveryRecord created = service.recordDelivery(delivery);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/po/{poId}")
    @Operation(summary = "Get deliveries by purchase order ID")
    public ResponseEntity<List<DeliveryRecord>> getDeliveriesByPO(@PathVariable Long poId) {
        List<DeliveryRecord> deliveries = service.getDeliveriesByPO(poId);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get delivery by ID")
    public ResponseEntity<DeliveryRecord> getDeliveryById(@PathVariable Long id) {
        DeliveryRecord delivery = service.getDeliveryById(id);
        return ResponseEntity.ok(delivery);
    }

    @GetMapping
    @Operation(summary = "Get all deliveries")
    public ResponseEntity<List<DeliveryRecord>> getAllDeliveries() {
        List<DeliveryRecord> deliveries = service.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }
}

