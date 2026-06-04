package com.poncheck.service.impl;

import com.poncheck.dto.request.cash.CashRegisterCloseRequestDTO;
import com.poncheck.dto.request.cash.CashRegisterOpenRequestDTO;
import com.poncheck.dto.request.cash.UpdateRegisterRequestDTO;
import com.poncheck.dto.response.cash.CashRegisterResponseDTO;
import com.poncheck.entity.Business;
import com.poncheck.entity.CashRegister;
import com.poncheck.entity.User;
import com.poncheck.enums.CashRegisterStatus;
import com.poncheck.enums.Role;
import com.poncheck.exception.InvalidCashRegisterException;
import com.poncheck.exception.InvalidDateRangeException;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.repository.CashRegisterRepository;
import com.poncheck.repository.UserRepository;
import com.poncheck.service.CashRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CashRegisterServiceImpl implements CashRegisterService {

    private final CashRegisterRepository repository;
    private final UserRepository userRepository;
    private final BusinessContextService businessContextService;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public CashRegisterResponseDTO openRegister(CashRegisterOpenRequestDTO data) {
        if(repository.existsByStatusAndBusinessId(CashRegisterStatus.OPEN, data.businessId())){
            throw new InvalidCashRegisterException("There is one Cash Register already open");
        }
        Business business = businessContextService.getBusiness(data.businessId());
        User currentUser = authenticatedUserService.getCurrentUser();

        CashRegister cashRegister = new CashRegister(
                data.openingAmount(),
                currentUser,
                data.description(),
                business
        );
        cashRegister.openRegister();
        CashRegister registerOpened = repository.save(cashRegister);
        return new CashRegisterResponseDTO(registerOpened);
    }

    @Override
    public CashRegisterResponseDTO closeRegister(Long id, CashRegisterCloseRequestDTO data) {
        CashRegister cashRegister = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Register Not Found", "cash_register", id));

        User user = authenticatedUserService.getCurrentUser();
        cashRegister.setClosedBy(user);
        cashRegister.setRealAmount(data.realAmount());
        cashRegister.setDescription(data.description());
        cashRegister.calculateDifference();
        cashRegister.closeRegister();

        CashRegister registerClosed = repository.save(cashRegister);
        return new CashRegisterResponseDTO(registerClosed);
    }

    @Override
    public CashRegisterResponseDTO getCurrentRegister(){
        User currentUser = authenticatedUserService.getCurrentUser();
        CashRegister register;
        if(currentUser.getRole() == Role.ADMIN){
            register = repository.findByStatus(CashRegisterStatus.OPEN)
                    .orElseThrow(() -> new ResourceNotFoundException("No open Cash Register", "cash_register", null));
        }else{
            register = repository.findByStatusAndBusiness_id(CashRegisterStatus.OPEN, currentUser.getBusiness().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("No open Cash Register", "cash_register", null));
        }


        return new CashRegisterResponseDTO(register);
    }

    @Override
    public CashRegisterResponseDTO getRegisterById(Long registerId){
        Long businessId = businessContextService.getCurrentBusiness().getId();
        CashRegister register = repository.findByIdAndBusiness_id(registerId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Register Not Found", "cash_register", registerId));

        return new CashRegisterResponseDTO(register);
    }

    @Override
    public List<CashRegisterResponseDTO> getRegistersByDateRange(LocalDateTime start, LocalDateTime end){
        if (start.isAfter(end)) {
            throw new InvalidDateRangeException("Start date cannot be after end date");
        }
        User currentUser = authenticatedUserService.getCurrentUser();
        List<CashRegister> registerList;
        if(currentUser.getRole() == Role.ADMIN){
            registerList = repository.findByOpenedAtBetween(start, end);
        }else{
            registerList = repository.findByOpenedAtBetweenAndBusinessId(start, end, currentUser.getBusiness().getId());
        }
        return  registerList.stream().map(CashRegisterResponseDTO::new).toList();

    }

    @Override
    public List<CashRegisterResponseDTO> getRegistersOpenedByUser(Long id){
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", "user", id));
        List<CashRegister> registerList = repository.findByOpenedBy_id(id);
        return  registerList.stream().map(CashRegisterResponseDTO::new).toList();

    }

    @Override
    public List<CashRegisterResponseDTO> getRegistersClosedByUser(Long id){
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", "user", id));
        List<CashRegister> registerList = repository.findByClosedBy_id(id);
        return  registerList.stream().map(CashRegisterResponseDTO::new).toList();

    }

    @Override
    public CashRegisterResponseDTO updateRegister(Long registerId, UpdateRegisterRequestDTO data){
        Long businessId = businessContextService.getBusiness(data.businessId()).getId();
        CashRegister register = repository.findByIdAndBusiness_id(registerId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Register Not Found", "cash_register", registerId));

        register.updateRegister(
                data.description(),
                data.realAmount()
        );
        CashRegister registerSaved = repository.save(register);
        return new CashRegisterResponseDTO(registerSaved);
    }
}
