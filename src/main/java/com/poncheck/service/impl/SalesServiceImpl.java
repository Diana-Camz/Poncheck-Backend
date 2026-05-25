package com.poncheck.service.impl;

import com.poncheck.dto.request.sales.CreateSaleRequestDTO;
import com.poncheck.dto.response.sales.SalesResponseDTO;
import com.poncheck.entity.Sales;
import com.poncheck.entity.User;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.repository.SalesRepository;
import com.poncheck.repository.UserRepository;
import com.poncheck.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final SalesRepository repository;
    private final UserRepository userRepository;

    @Override
    public List<SalesResponseDTO> getAllSales(){
        List<Sales> salesList = repository.findAll();
       return salesList.stream()
               .map(SalesResponseDTO::new)
               .toList();
    }

    @Override
    public SalesResponseDTO createSale(CreateSaleRequestDTO data){
        User user = userRepository.findById(data.userId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

       Sales sale = new Sales(
               data.total(),
               data.paymentMethod(),
               data.description(),
               user

       );
       Sales saleSaved =repository.save(sale);
       return new SalesResponseDTO(saleSaved);
    }
}
