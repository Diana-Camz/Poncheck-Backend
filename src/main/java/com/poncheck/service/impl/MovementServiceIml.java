package com.poncheck.service.impl;

import com.poncheck.dto.request.inventory.CreateMovementRequestDTO;
import com.poncheck.dto.request.inventory.UpdateMovementRequestDTO;
import com.poncheck.dto.response.inventory.MovementItemResponseDTO;
import com.poncheck.dto.response.inventory.MovementResponseDTO;
import com.poncheck.entity.*;
import com.poncheck.enums.Role;
import com.poncheck.enums.TypeInventoryMovement;
import com.poncheck.exception.InvalidMovementException;
import com.poncheck.exception.ResourceDisabledException;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.repository.MovementRepository;
import com.poncheck.repository.ProductRepository;
import com.poncheck.repository.SalesRepository;
import com.poncheck.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovementServiceIml implements MovementService {

    private final MovementRepository repository;
    private final ProductRepository productRepository;
    private final SalesRepository saleRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final BusinessContextService businessContextService;


    @Override
    public List<MovementItemResponseDTO> getMovementsByType(TypeInventoryMovement type){
        User currentUser = authenticatedUserService.getCurrentUser();
        List<Movement> typeList;
        if (currentUser.getRole() == Role.ADMIN) {
            typeList = repository.findMovementByTypeInventoryMovement(type);
        }else{
            Business business = currentUser.getBusiness();
            typeList = repository.findMovementByTypeInventoryMovementAndBusinessId(type, business.getId());
        }

        return typeList.stream().map(MovementItemResponseDTO::new).toList();
    }

    @Override
    public List<MovementItemResponseDTO> getMovementsByProduct(Long id){
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found", "product", id));
        Long businessId = businessContextService.getCurrentBusiness().getId();
        List<Movement> productList = repository.findMovementsByProductIdAndBusiness_id(id, businessId);
        return productList.stream().map(MovementItemResponseDTO::new).toList();
    }

    @Override
    public List<MovementItemResponseDTO> getMovementsBySale(Long saleId){
        Long businessId = businessContextService.getCurrentBusiness().getId();
        saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale Not Found", "sales", saleId));
        List<Movement> saleList = repository.findMovementsBySale_idAndBusiness_id(saleId, businessId);
        return saleList.stream().map(MovementItemResponseDTO::new).toList();
    }

    @Override
    public MovementResponseDTO getMovementById(Long saleId){
        Long businessId = businessContextService.getCurrentBusiness().getId();
        Movement movement = repository.findByIdAndBusiness_id(saleId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Movement Not Found", "inventory_movement", saleId));
        return new MovementResponseDTO(movement);
    }

    @Transactional
    @Override
    public List<MovementItemResponseDTO> createMovement(CreateMovementRequestDTO data) {
        User user = authenticatedUserService.getCurrentUser();
        Business business = businessContextService.getBusiness(data.businessId());
        Sales sale = null;
        if (data.saleId() != null) {
            sale = saleRepository.findByIdAndBusiness_id(data.saleId(), business.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sale Not Found", "sale", data.saleId()));
        }
        Movement movementReference = null;
        if (data.referenceMovement() != null){
            movementReference = repository.findByIdAndBusiness_id(data.referenceMovement(), business.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Movement Reference Not Found", "inventory_movement", data.referenceMovement()));
        }

        List<Movement> movements = data.products().stream().map((item) -> {
            Product product = productRepository.findByIdAndBusiness_id(item.productId(), business.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product  Not Found", "product", item.productId()));
            if(!product.getActive()){
                throw new ResourceDisabledException("Product is disabled", product.getId());
            }

            if(!data.type().isManualAllowed()){
                throw new InvalidMovementException("Movements of type Sale or Sale Cancelled are not permitted manually");
            }
            if (data.type().isAddsStock()) {
                product.increaseStock(item.quantity());
            } else {
                product.decreaseStock(item.quantity());
            }
            return new Movement(
                    data.type(),
                    item.quantity(),
                    data.description(),
                    user,
                    product,
                    null,
                    null,
                    business
            );

        }).toList();
        List<Movement> savedMovements = repository.saveAll(movements);

        return savedMovements.stream().map(MovementItemResponseDTO::new).toList();

    }

    @Override
    public MovementResponseDTO updateMovement(Long id, UpdateMovementRequestDTO data){
        Long businessId = businessContextService.getCurrentBusiness().getId();
        Movement movement = repository.findByIdAndBusiness_id(id, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Movement Not Found", "inventory_movement", id));
        movement.updateMovement(
                data.description()
        );

        Movement movementSaved = repository.save(movement);
        return new MovementResponseDTO(movementSaved);
    }
}
