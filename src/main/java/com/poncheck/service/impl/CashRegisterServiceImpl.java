package com.poncheck.service.impl;

import com.poncheck.dto.request.cash.CashRegisterCloseRequestDTO;
import com.poncheck.dto.request.cash.CashRegisterOpenRequestDTO;
import com.poncheck.dto.request.cash.UpdateRegisterRequestDTO;
import com.poncheck.dto.response.cash.CashRegisterResponseDTO;
import com.poncheck.entity.CashRegister;
import com.poncheck.entity.User;
import com.poncheck.enums.CashRegisterStatus;
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

    @Override
    public CashRegisterResponseDTO openRegister(CashRegisterOpenRequestDTO data) {
        if(repository.existsByStatus(CashRegisterStatus.OPEN)){
            throw new InvalidCashRegisterException("There is one Cash Register already open");
        }
        User user = userRepository.findById(data.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", "user", data.userId()));

        CashRegister cashRegister = new CashRegister(
                data.openingAmount(),
                user,
                data.description()
        );
        cashRegister.openRegister();
        CashRegister registerOpened = repository.save(cashRegister);
        return new CashRegisterResponseDTO(registerOpened);
    }

    @Override
    public CashRegisterResponseDTO closeRegister(Long id, CashRegisterCloseRequestDTO data) {
        CashRegister cashRegister = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Register Not Found", "cash_register", id));

        User user = userRepository.findById(data.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", "user", data.userId()));
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
        CashRegister register = repository.findByStatus(CashRegisterStatus.OPEN)
                .orElseThrow(() -> new ResourceNotFoundException("No open Cash Register", "cash_register", null));

        return new CashRegisterResponseDTO(register);
    }

    @Override
    public CashRegisterResponseDTO getRegisterById(Long id){
        CashRegister register = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Register Not Found", "cash_register", id));

        return new CashRegisterResponseDTO(register);
    }

    @Override
    public List<CashRegisterResponseDTO> getRegistersByDateRange(LocalDateTime start, LocalDateTime end){
        if (start.isAfter(end)) {
            throw new InvalidDateRangeException("Start date cannot be after end date");
        }
        List<CashRegister> registerList = repository.findByOpenedAtBetween(start, end);
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
    public CashRegisterResponseDTO updateRegister(Long id, UpdateRegisterRequestDTO data){
        CashRegister register = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Register Not Found", "cash_register", id));

        register.updateRegister(
                data.description(),
                data.realAmount()
        );
        CashRegister registerSaved = repository.save(register);
        return new CashRegisterResponseDTO(registerSaved);
    }
}
