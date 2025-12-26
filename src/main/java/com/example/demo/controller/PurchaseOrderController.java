package com.example.demo.controller;

import com.example.demo.model.PurchaseOrderRecord;
import com.example.demo.service.impl.PurchaseOrderServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderServiceImpl service;

    public PurchaseOrderController(PurchaseOrderServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public PurchaseOrderRecord create(@RequestBody PurchaseOrderRecord po) {
        return service.createPurchaseOrder(po);
    }

    @GetMapping("/{id}")
    public PurchaseOrderRecord getById(@PathVariable Long id) {
        // ✅ NO orElse here
        return service.getPOById(id);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<PurchaseOrderRecord> getBySupplier(@PathVariable Long supplierId) {
        return service.getPOsBySupplier(supplierId);
    }

    @GetMapping
    public List<PurchaseOrderRecord> getAll() {
        return service.getAllPurchaseOrders();
    }
}
