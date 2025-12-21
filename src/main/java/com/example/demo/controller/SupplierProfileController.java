package com.example.demo.controller;

import com.example.demo.model.SupplierProfile;
import com.example.demo.service.SupplierProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierProfileController {
    
    private final SupplierProfileService supplierProfileService;
    
    public SupplierProfileController(SupplierProfileService supplierProfileService) {
        this.supplierProfileService = supplierProfileService;
    }
    
    @PostMapping
    public ResponseEntity<SupplierProfile> createSupplier(@RequestBody SupplierProfile supplier) {
        SupplierProfile created = supplierProfileService.createSupplier(supplier);
        return ResponseEntity.ok(created);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SupplierProfile> getSupplierById(@PathVariable Long id) {
        SupplierProfile supplier = supplierProfileService.getSupplierById(id);
        return ResponseEntity.ok(supplier);
    }
    
    @GetMapping
    public ResponseEntity<List<SupplierProfile>> getAllSuppliers() {
        List<SupplierProfile> suppliers = supplierProfileService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<SupplierProfile> updateSupplierStatus(
            @PathVariable Long id, 
            @RequestParam boolean active) {
        SupplierProfile supplier = supplierProfileService.updateSupplierStatus(id, active);
        return ResponseEntity.ok(supplier);
    }
    
    @GetMapping("/lookup/{supplierCode}")
    public ResponseEntity<SupplierProfile> getBySupplierCode(@PathVariable String supplierCode) {
        Optional<SupplierProfile> supplier = supplierProfileService.getBySupplierCode(supplierCode);
        return supplier.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

