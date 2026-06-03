package com.poncheck.service.impl;

import com.poncheck.dto.request.cash.CashMovementCreateRequestDTO;
import com.poncheck.dto.request.cash.UpdateCashMovementRequestDTO;
import com.poncheck.dto.response.cash.CashMovementResponseDTO;
import com.poncheck.entity.*;
import com.poncheck.enums.CashRegisterStatus;
import com.poncheck.enums.Role;
import com.poncheck.enums.TypeCashMovement;
import com.poncheck.exception.InvalidCashMovementException;
import com.poncheck.exception.InvalidCashRegisterException;
import com.poncheck.exception.InvalidDateRangeException;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.repository.*;
import com.poncheck.service.CashMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CashMovementServiceImpl implements CashMovementService {
    private final CashMovementRepository repository;
    private final SalesRepository saleRepository;
    private final UserRepository userRepository;
    private final CashRegisterRepository registerRepository;
    private final CancelledSaleRepository cancelledSaleRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final BusinessContextService businessContextService;

    @Override
    public List<CashMovementResponseDTO> getMovementsByType(TypeCashMovement type){
        User currentUser = authenticatedUserService.getCurrentUser();
        List<CashMovement> movementList;
        if(currentUser.getRole() == Role.ADMIN){
            movementList = repository.findCashMovementByTypeCashMovement(type);
        }else{
            movementList = repository.findCashMovementByTypeCashMovementAndBusiness_id(type, currentUser.getBusiness().getId());
        }
        return movementList.stream().map(CashMovementResponseDTO::new).toList();
    }

    @Override
    public List<CashMovementResponseDTO> getMovementsBySale(Long id){
        saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale Not Found", "sales", id));
        User currentUser = authenticatedUserService.getCurrentUser();
        List<CashMovement> movementList;
        if(currentUser.getRole() == Role.ADMIN){
            movementList = repository.findCashMovementBySaleId(id);
        }else {
            movementList = repository.findCashMovementBySaleIdAndBusiness_id(id, currentUser.getBusiness().getId());
        }
        return movementList.stream().map(CashMovementResponseDTO::new).toList();
    }

    @Override
    public CashMovementResponseDTO getMovementById(Long id){
        CashMovement movement = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movement Not Found", "cash_movement", id));
        return new CashMovementResponseDTO(movement);
    }

    @Override
    public List<CashMovementResponseDTO> getCashMovementsByDateRange(LocalDateTime start, LocalDateTime end){
        if (start.isAfter(end)) {
            throw new InvalidDateRangeException("Start date cannot be after end date");
        }
        User currentUser = authenticatedUserService.getCurrentUser();
        List<CashMovement> movementList;
        if(currentUser.getRole() == Role.ADMIN){
            movementList = repository.findByMovementAtBetween(start, end);
        }else{
            movementList = repository.findByMovementAtBetweenAndBusiness_id(start, end, currentUser.getBusiness().getId());
        }

        return movementList.stream().map(CashMovementResponseDTO::new).toList();
    }

    @Override
    public List<CashMovementResponseDTO> getMovementsBySalesByDateRange(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new InvalidDateRangeException("Start date cannot be after end date");
        }
        User currentUser = authenticatedUserService.getCurrentUser();
        List<CashMovement> movementList = repository.findBySale_dateBetweenAndBusiness_id(start, end, currentUser.getBusiness().getId());

        return movementList.stream().map(CashMovementResponseDTO::new).toList();
    }
    @Override
    public List<CashMovementResponseDTO> getCashMovementsByUser(Long id){
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", "user", id));
        User currentUser = authenticatedUserService.getCurrentUser();
        List<CashMovement> movementList;
        if(currentUser.getRole() == Role.ADMIN){
            movementList = repository.findByUser_id(id);
        }else{
            movementList = repository.findByUser_idAndBusiness_id(id, currentUser.getBusiness().getId());
        }

        return movementList.stream().map(CashMovementResponseDTO::new).toList();
    }

    @Transactional
    @Override
    public CashMovementResponseDTO createMovement(CashMovementCreateRequestDTO data) {
        User user = authenticatedUserService.getCurrentUser();
        Business business = businessContextService.getBusiness(data.businessId());
        CashRegister cashRegister = registerRepository.findById(data.cashRegisterId())
                .orElseThrow(() -> new ResourceNotFoundException("Cash Register Not Found", "cash_register", data.cashRegisterId()));

        if(cashRegister.getStatus() == CashRegisterStatus.CLOSED){
            throw new InvalidCashRegisterException("Cash register is closed");
        }

        Sales sale = null;
        if (data.saleId() != null) {
            sale = saleRepository.findById(data.saleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sale Not Found", "sale", data.saleId()));
        }
        CancelledSale cancelledSale = null;
        if (data.cancelledSaleId() != null) {
            cancelledSale = cancelledSaleRepository.findById(data.cancelledSaleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cancelled Sale Not Found", "cancelled_sale", data.cancelledSaleId()));
        }

        if(!data.type().isManualAllowed()){
            throw new InvalidCashMovementException("Movements of type Sale or Refund are not permitted manually");
        }

        if(data.type().isAddCash()){
            cashRegister.increaseExpectedAmount(data.amount());
        }else{
            cashRegister.decreaseExpectedAmount(data.amount());
        }

        CashMovement movement = new CashMovement(
                data.type(),
                data.amount(),
                user,
                sale,
                cancelledSale,
                cashRegister,
                data.description(),
                business
        );

        registerRepository.save(cashRegister);
        CashMovement movementSaved = repository.save(movement);
        return new CashMovementResponseDTO(movementSaved);
    }

    @Transactional
    @Override
    public CashMovementResponseDTO updateMovement(Long id, UpdateCashMovementRequestDTO data){
        CashMovement movement = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movement Not Found", "cash_movement", id));

        CashRegister cashRegister = registerRepository.findById(movement.getCashRegister().getId())
                        .orElseThrow(() -> new InvalidCashRegisterException("Cash Register Not Found"));

        BigDecimal oldAmount = movement.getAmount();

        movement.updateMovement(data.description(), data.amount());
        if(data.amount() != null){
            BigDecimal difference = data.amount().subtract(oldAmount);
            BigDecimal amount = difference.abs();

            if(difference.compareTo(BigDecimal.ZERO) < 0){
                if(movement.getTypeCashMovement().isAddCash()){
                    cashRegister.decreaseExpectedAmount(amount);
                }else{
                    cashRegister.increaseExpectedAmount(amount);
                }
            }else if(difference.compareTo(BigDecimal.ZERO) > 0){
                if(movement.getTypeCashMovement().isAddCash()){
                    cashRegister.increaseExpectedAmount(amount);
                }else{
                    cashRegister.decreaseExpectedAmount(amount);
                }
            }
        }

        CashMovement movementSaved = repository.save(movement);
        registerRepository.save(cashRegister);
        return new CashMovementResponseDTO(movementSaved);
    }
}
