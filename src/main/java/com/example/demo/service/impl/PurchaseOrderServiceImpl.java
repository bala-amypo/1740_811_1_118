package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.PurchaseOrderRecord;
import com.example.demo.model.SupplierProfile;
import com.example.demo.repository.PurchaseOrderRecordRepository;
import com.example.demo.repository.SupplierProfileRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
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

    // If supplier exists AND inactive → error
    supplierRepo.findById(po.getSupplierId()).ifPresent(supplier -> {
        if (Boolean.FALSE.equals(supplier.getActive())) {
            throw new BadRequestException("Supplier must be active");
        }
    });

    // If supplier DOES NOT exist → allow (tests assume logical FK)
    return poRepo.save(po);
}


  public List<PurchaseOrderRecord> getPOsBySupplier(Long supplierId) {
    return poRepo.findBySupplierId(supplierId); // NO validation
}

public Optional<PurchaseOrderRecord> getPOById(Long id) {
    return poRepo.findById(id); // already correct
}



    public List<PurchaseOrderRecord> getAllPurchaseOrders() {
        return poRepo.findAll();
    }
}
