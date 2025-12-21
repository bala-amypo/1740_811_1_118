package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "supplier_risk_alerts")
public class SupplierRiskAlert {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long supplierId;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AlertLevel alertLevel;
    
    private String message;
    
    @Column(nullable = false)
    private LocalDateTime alertDate;
    
    @Column(nullable = false)
    private Boolean resolved = false;
    
    @PrePersist
    protected void onCreate() {
        if (alertDate == null) {
            alertDate = LocalDateTime.now();
        }
    }
    
    // Enum for Alert Level
    public enum AlertLevel {
        LOW, MEDIUM, HIGH
    }
    
    // Constructors
    public SupplierRiskAlert() {
    }
    
    public SupplierRiskAlert(Long supplierId, AlertLevel alertLevel, String message) {
        this.supplierId = supplierId;
        this.alertLevel = alertLevel;
        this.message = message;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getSupplierId() {
        return supplierId;
    }
    
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    
    public AlertLevel getAlertLevel() {
        return alertLevel;
    }
    
    public void setAlertLevel(AlertLevel alertLevel) {
        this.alertLevel = alertLevel;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public LocalDateTime getAlertDate() {
        return alertDate;
    }
    
    public void setAlertDate(LocalDateTime alertDate) {
        this.alertDate = alertDate;
    }
    
    public Boolean getResolved() {
        return resolved;
    }
    
    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }
}

