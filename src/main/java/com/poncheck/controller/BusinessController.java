package com.poncheck.controller;

import com.poncheck.dto.request.business.CreateBusinessRequestDTO;
import com.poncheck.dto.request.business.UpdateActiveBusinessRequestDTO;
import com.poncheck.dto.request.business.UpdateBusinessRequestDTO;
import com.poncheck.dto.request.business.UpdateOwnerBusinessRequestDTO;
import com.poncheck.dto.response.business.BusinessResponseDTO;
import com.poncheck.service.BusinessService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Businesses", description = "Endpoints for managing businesses, including creation, updates, activation, owner assignment, and deletion.")
@RestController
@RequestMapping("/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService service;

    @Operation(summary = "Get business by ID", description = "Retrieves detailed information about a specific business.")
    @GetMapping("/{id}")
    public ResponseEntity<BusinessResponseDTO> getBusinessById(@PathVariable Long id){
        BusinessResponseDTO business = service.getBusinessById(id);
        return ResponseEntity.ok(business);
    }

    @Operation(summary = "Get active businesses", description = "Retrieves all businesses currently marked as active.")
    @GetMapping("/active")
    public ResponseEntity<List<BusinessResponseDTO>> getActiveBusiness(){
        List<BusinessResponseDTO> businessList = service.getActiveBusiness();
        return ResponseEntity.ok(businessList);
    }

    @Operation(summary = "Get inactive businesses", description = "Retrieves all businesses currently marked as inactive.")
    @GetMapping("/inactive")
    public ResponseEntity<List<BusinessResponseDTO>> getInactiveBusiness(){
        List<BusinessResponseDTO> businessList = service.getInactiveBusiness();
        return ResponseEntity.ok(businessList);
    }

    @Operation(summary = "Get businesses by owner", description = "Retrieves business associated with a specific owner.")
    @GetMapping("/owner/{id}")
    public ResponseEntity<List<BusinessResponseDTO>> getBusinessByOwner(@PathVariable Long id){
        List<BusinessResponseDTO> businessList = service.getBusinessByOwner(id);
        return ResponseEntity.ok(businessList);
    }

    @Operation(summary = "Create business", description = "Creates a new business and registers its initial information.")
    @PostMapping
    public ResponseEntity<BusinessResponseDTO> createBusiness(@RequestBody CreateBusinessRequestDTO data){
        BusinessResponseDTO business = service.createBusiness(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(business);
    }

    @Operation(summary = "Update business", description = "Updates business information such as name, contact details, address, and description."
    )
    @PutMapping("/{id}")
    public ResponseEntity<BusinessResponseDTO> updateBusiness(@PathVariable Long id, @RequestBody UpdateBusinessRequestDTO data){
        BusinessResponseDTO business = service.updateBusiness(id, data);
        return ResponseEntity.ok(business);
    }

    @Operation(summary = "Update business active status", description = "Activates or deactivates a business without permanently removing it from the system.")
    @PatchMapping("/{id}/active")
    public ResponseEntity<Void> updateActive(@PathVariable Long id, @RequestBody UpdateActiveBusinessRequestDTO data){
        service.updateActive(id, data);
        return ResponseEntity.noContent().build();
    }

    //@Operation(summary = "Assign business owner", description = "Assigns or updates the primary owner of a business.")
    @Hidden
    @PatchMapping("/{id}/owner")
    public ResponseEntity<BusinessResponseDTO> updateOwner(@PathVariable Long id, @RequestBody UpdateOwnerBusinessRequestDTO data){
        BusinessResponseDTO business = service.updateOwner(id, data);
        return ResponseEntity.ok(business);
    }

    //@Operation(summary = "Delete business", description = "Permanently removes a business from the system.")
    @Hidden
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusiness(@PathVariable Long id){
        service.deleteBusiness(id);
        return ResponseEntity.noContent().build();
    }
}
