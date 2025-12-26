package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.PurchaseOrderRecord;
import com.example.demo.repository.PurchaseOrderRecordRepository;
import com.example.demo.repository.SupplierProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderRecordRepository repository;
    private final SupplierProfileRepository supplierRepository;

    public PurchaseOrderServiceImpl(PurchaseOrderRecordRepository repository, 
                                   SupplierProfileRepository supplierRepository) {
        this.repository = repository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public PurchaseOrderRecord createPurchaseOrder(PurchaseOrderRecord po) {
        // Validate supplier ID
        if (!supplierRepository.existsById(po.getSupplierId())) {
            throw new BadRequestException("Invalid supplierId");
        }

        // Validate supplier is active
        var supplier = supplierRepository.findById(po.getSupplierId())
                .orElseThrow(() -> new BadRequestException("Invalid supplierId"));
        if (!supplier.getActive()) {
            throw new BadRequestException("Supplier must be active");
        }

        // Validate quantity
        if (po.getQuantity() == null || po.getQuantity() <= 0) {
            throw new BadRequestException("Quantity must be positive");
        }

        return repository.save(po);
    }

    @Override
    public List<PurchaseOrderRecord> getPOsBySupplier(Long supplierId) {
        return repository.findBySupplierId(supplierId);
    }

    @Override
    public PurchaseOrderRecord getPOById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found"));
    }

    @Override
    public List<PurchaseOrderRecord> getAllPurchaseOrders() {
        return repository.findAll();
    }
}

