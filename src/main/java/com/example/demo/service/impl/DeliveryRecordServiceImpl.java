package com.example.demo.service.impl;

import com.example.demo.model.DeliveryRecord;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.service.DeliveryRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryRecordServiceImpl implements DeliveryRecordService {
    
    private final DeliveryRecordRepository deliveryRecordRepository;
    
    public DeliveryRecordServiceImpl(DeliveryRecordRepository deliveryRecordRepository) {
        this.deliveryRecordRepository = deliveryRecordRepository;
    }
    
    @Override
    public DeliveryRecord recordDelivery(DeliveryRecord delivery) {
        // Validate delivered quantity
        if (delivery.getDeliveredQuantity() == null || delivery.getDeliveredQuantity() < 0) {
            throw new IllegalArgumentException("Delivered quantity must be non-negative");
        }
        
        return deliveryRecordRepository.save(delivery);
    }
    
    @Override
    public List<DeliveryRecord> getDeliveriesByPO(Long poId) {
        return deliveryRecordRepository.findByPoId(poId);
    }
    
    @Override
    public DeliveryRecord getDeliveryById(Long id) {
        return deliveryRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found with id: " + id));
    }
    
    @Override
    public List<DeliveryRecord> getAllDeliveries() {
        return deliveryRecordRepository.findAll();
    }
}

