package com.poncheck.controller;

import com.poncheck.dto.request.business.CreateBusinessRequestDTO;
import com.poncheck.dto.request.business.UpdateActiveBusinessRequestDTO;
import com.poncheck.dto.request.business.UpdateBusinessRequestDTO;
import com.poncheck.dto.request.business.UpdateOwnerBusinessRequestDTO;
import com.poncheck.dto.response.business.BusinessResponseDTO;
import com.poncheck.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService service;

    @GetMapping("/{id}")
    public ResponseEntity<BusinessResponseDTO> getBusinessById(@PathVariable Long id){
        BusinessResponseDTO business = service.getBusinessById(id);
        return ResponseEntity.ok(business);
    }

    @GetMapping("/active")
    public ResponseEntity<List<BusinessResponseDTO>> getActiveBusiness(){
        List<BusinessResponseDTO> businessList = service.getActiveBusiness();
        return ResponseEntity.ok(businessList);
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<BusinessResponseDTO>> getInactiveBusiness(){
        List<BusinessResponseDTO> businessList = service.getInactiveBusiness();
        return ResponseEntity.ok(businessList);
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<List<BusinessResponseDTO>> getBusinessByOwner(@PathVariable Long id){
        List<BusinessResponseDTO> businessList = service.getBusinessByOwner(id);
        return ResponseEntity.ok(businessList);
    }

    @PostMapping
    public ResponseEntity<BusinessResponseDTO> createBusiness(@RequestBody CreateBusinessRequestDTO data){
        BusinessResponseDTO business = service.createBusiness(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(business);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessResponseDTO> updateBusiness(@PathVariable Long id, @RequestBody UpdateBusinessRequestDTO data){
        BusinessResponseDTO business = service.updateBusiness(id, data);
        return ResponseEntity.ok(business);
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<Void> updateActive(@PathVariable Long id, @RequestBody UpdateActiveBusinessRequestDTO data){
        service.updateActive(id, data);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/owner")
    public ResponseEntity<BusinessResponseDTO> updateOwner(@PathVariable Long id, @RequestBody UpdateOwnerBusinessRequestDTO data){
        BusinessResponseDTO business = service.updateOwner(id, data);
        return ResponseEntity.ok(business);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusiness(@PathVariable Long id){
        service.deleteBusiness(id);
        return ResponseEntity.noContent().build();
    }
}
