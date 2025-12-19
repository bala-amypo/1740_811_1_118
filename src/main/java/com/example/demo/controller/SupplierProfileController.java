package com.example.demo.controller;

import com.example.demo.model.SupplierProfile;
import com.example.demo.service.SupplierProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Supplier Profile", description = "Supplier profile management endpoints")
public class SupplierProfileController {
    private final SupplierProfileService service;

    public SupplierProfileController(SupplierProfileService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new supplier")
    public ResponseEntity<SupplierProfile> createSupplier(@RequestBody SupplierProfile supplier) {
        SupplierProfile created = service.createSupplier(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get supplier by ID")
    public ResponseEntity<SupplierProfile> getSupplierById(@PathVariable Long id) {
        SupplierProfile supplier = service.getSupplierById(id);
        return ResponseEntity.ok(supplier);
    }

    @GetMapping
    @Operation(summary = "Get all suppliers")
    public ResponseEntity<List<SupplierProfile>> getAllSuppliers() {
        List<SupplierProfile> suppliers = service.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update supplier status")
    public ResponseEntity<SupplierProfile> updateSupplierStatus(@PathVariable Long id, 
                                                               @RequestParam boolean active) {
        SupplierProfile updated = service.updateSupplierStatus(id, active);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/lookup/{supplierCode}")
    @Operation(summary = "Lookup supplier by code")
    public ResponseEntity<SupplierProfile> lookupByCode(@PathVariable String supplierCode) {
        return service.getBySupplierCode(supplierCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

