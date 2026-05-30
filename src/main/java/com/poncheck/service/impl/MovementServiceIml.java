package com.poncheck.service.impl;

import com.poncheck.dto.request.inventory.CreateMovementRequestDTO;
import com.poncheck.dto.request.inventory.UpdateMovementRequestDTO;
import com.poncheck.dto.response.inventory.MovementItemResponseDTO;
import com.poncheck.dto.response.inventory.MovementResponseDTO;
import com.poncheck.entity.Movement;
import com.poncheck.entity.Product;
import com.poncheck.entity.Sales;
import com.poncheck.entity.User;
import com.poncheck.enums.TypeInventoryMovement;
import com.poncheck.exception.InvalidMovementException;
import com.poncheck.exception.ResourceDisabledException;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.repository.MovementRepository;
import com.poncheck.repository.ProductRepository;
import com.poncheck.repository.SalesRepository;
import com.poncheck.repository.UserRepository;
import com.poncheck.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovementServiceIml implements MovementService {

    private final MovementRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SalesRepository saleRepository;


    @Override
    public List<MovementItemResponseDTO> getMovementsByType(TypeInventoryMovement type){
        List<Movement> typeList = repository.findMovementByTypeInventoryMovement(type);
        return typeList.stream().map(MovementItemResponseDTO::new).toList();
    }

    @Override
    public List<MovementItemResponseDTO> getMovementsByProduct(Long id){
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found", "product", id));
        List<Movement> productList = repository.findMovementsByProductId(id);
        return productList.stream().map(MovementItemResponseDTO::new).toList();
    }

    @Override
    public List<MovementItemResponseDTO> getMovementsBySale(Long id){
        saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale Not Found", "sales", id));
        List<Movement> saleList = repository.findMovementsBySale_Id(id);
        return saleList.stream().map(MovementItemResponseDTO::new).toList();
    }

    @Override
    public MovementResponseDTO getMovementById(Long id){
        Movement movement = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movement Not Found", "inventory_movement", id));
        return new MovementResponseDTO(movement);
    }

    @Transactional
    @Override
    public List<MovementItemResponseDTO> createMovement(CreateMovementRequestDTO data) {
        User user = userRepository.findById(data.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", "user", data.userId()));
        Sales sale = null;
        if (data.saleId() != null) {
            sale = saleRepository.findById(data.saleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sale Not Found", "sale", data.saleId()));
        }
        Movement movementReference = null;
        if (data.referenceMovement() != null){
            movementReference = repository.findById(data.referenceMovement())
                    .orElseThrow(() -> new ResourceNotFoundException("Movement Reference Not Found", "inventory_movement", data.referenceMovement()));
        }

        List<Movement> movements = data.products().stream().map((item) -> {
            Product product = productRepository.findById(item.productId())
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
                    null
            );

        }).toList();
        List<Movement> savedMovements = repository.saveAll(movements);

        return savedMovements.stream().map(MovementItemResponseDTO::new).toList();

    }

    @Override
    public MovementResponseDTO updateMovement(Long id, UpdateMovementRequestDTO data){
        Movement movement = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movement Not Found", "inventory_movement", id));
        movement.updateMovement(
                data.description()
        );

        Movement movementSaved = repository.save(movement);
        return new MovementResponseDTO(movementSaved);
    }
}
