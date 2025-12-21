package com.example.demo.controller
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/suppliers")
class SupplierProfileController{
    private final SupplierProfileService service;
    public SupplierProfileController(SupplierProfileService service){
        this.service=service;
    }
    @PostMapping("/createsupplier")
    SupplierProfile createSupplier(Requestbody SupplierProfile supplier){
        return service.createSupplier(supplier);
    }
    @GetMapping("/getsupplier")
    List



}