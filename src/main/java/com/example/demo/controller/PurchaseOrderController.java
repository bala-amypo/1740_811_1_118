package com.example.demo.controller;

import com.example.demo.model.PurchaseOrderRecord;
import com.example.demo.service.PurchaseOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
@Tag(name = "Purchase Orders", description = "Purchase order management endpoints")
public class PurchaseOrderController {
    private final PurchaseOrderService service;

    public PurchaseOrderController(PurchaseOrderService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new purchase order")
    public ResponseEntity<PurchaseOrderRecord> createPurchaseOrder(@RequestBody PurchaseOrderRecord po) {
        PurchaseOrderRecord created = service.createPurchaseOrder(po);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/supplier/{supplierId}")
    @Operation(summary = "Get purchase orders by supplier ID")
    public ResponseEntity<List<PurchaseOrderRecord>> getPOsBySupplier(@PathVariable Long supplierId) {
        List<PurchaseOrderRecord> pos = service.getPOsBySupplier(supplierId);
        return ResponseEntity.ok(pos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get purchase order by ID")
    public ResponseEntity<PurchaseOrderRecord> getPOById(@PathVariable Long id) {
        PurchaseOrderRecord po = service.getPOById(id);
        return ResponseEntity.ok(po);
    }

    @GetMapping
    @Operation(summary = "Get all purchase orders")
    public ResponseEntity<List<PurchaseOrderRecord>> getAllPurchaseOrders() {
        List<PurchaseOrderRecord> pos = service.getAllPurchaseOrders();
        return ResponseEntity.ok(pos);
    }
}

