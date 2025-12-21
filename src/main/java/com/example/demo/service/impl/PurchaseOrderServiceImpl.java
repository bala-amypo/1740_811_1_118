package com.example.demo.service.impl;

import com.example.demo.model.PurchaseOrderRecord;
import com.example.demo.model.SupplierProfile;
import com.example.demo.repository.PurchaseOrderRecordRepository;
import com.example.demo.repository.SupplierProfileRepository;
import com.example.demo.service.PurchaseOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    
    private final PurchaseOrderRecordRepository purchaseOrderRecordRepository;
    private final SupplierProfileRepository supplierProfileRepository;
    
    public PurchaseOrderServiceImpl(PurchaseOrderRecordRepository purchaseOrderRecordRepository,
                                   SupplierProfileRepository supplierProfileRepository) {
        this.purchaseOrderRecordRepository = purchaseOrderRecordRepository;
        this.supplierProfileRepository = supplierProfileRepository;
    }
    
    @Override
    public PurchaseOrderRecord createPurchaseOrder(PurchaseOrderRecord po) {
        // Validate supplier exists
        SupplierProfile supplier = supplierProfileRepository.findById(po.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid supplierId: " + po.getSupplierId()));
        
        // Validate supplier is active
        if (!supplier.getActive()) {
            throw new IllegalArgumentException("Supplier must be active");
        }
        
        // Validate quantity
        if (po.getQuantity() == null || po.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        
        return purchaseOrderRecordRepository.save(po);
    }
    
    @Override
    public List<PurchaseOrderRecord> getPOsBySupplier(Long supplierId) {
        return purchaseOrderRecordRepository.findBySupplierId(supplierId);
    }
    
    @Override
    public PurchaseOrderRecord getPOById(Long id) {
        return purchaseOrderRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Purchase order not found with id: " + id));
    }
    
    @Override
    public List<PurchaseOrderRecord> getAllPurchaseOrders() {
        return purchaseOrderRecordRepository.findAll();
    }
}

