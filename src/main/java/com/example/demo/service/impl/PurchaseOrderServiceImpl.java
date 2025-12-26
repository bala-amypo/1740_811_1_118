package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.PurchaseOrderRecord;
import com.example.demo.model.SupplierProfile;
import com.example.demo.repository.PurchaseOrderRecordRepository;
import com.example.demo.repository.SupplierProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderServiceImpl {

    private final PurchaseOrderRecordRepository poRepo;
    private final SupplierProfileRepository supplierRepo;

    public PurchaseOrderServiceImpl(PurchaseOrderRecordRepository poRepo,
                                    SupplierProfileRepository supplierRepo) {
        this.poRepo = poRepo;
        this.supplierRepo = supplierRepo;
    }

    public PurchaseOrderRecord createPurchaseOrder(PurchaseOrderRecord po) {

        SupplierProfile supplier = supplierRepo.findById(po.getSupplierId())
                .orElseThrow(() -> new BadRequestException("Invalid supplierId"));

        if (!Boolean.TRUE.equals(supplier.getActive())) {
            throw new BadRequestException("Supplier is inactive");
        }

        return poRepo.save(po);
    }

    public List<PurchaseOrderRecord> getPOsBySupplier(Long supplierId) {
        return poRepo.findBySupplierId(supplierId);
    }

    public Optional<PurchaseOrderRecord> getPOById(Long id) {
    return poRepo.findById(id);
}


    public List<PurchaseOrderRecord> getAllPurchaseOrders() {
        return poRepo.findAll();
    }
}
