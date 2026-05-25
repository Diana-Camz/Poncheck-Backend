package com.poncheck.service.impl;

import com.poncheck.dto.request.sales.CreateSaleRequestDTO;
import com.poncheck.dto.request.sales.SaleItemRequestDTO;
import com.poncheck.dto.response.sales.SalesResponseDTO;
import com.poncheck.entity.Product;
import com.poncheck.entity.SaleItem;
import com.poncheck.entity.Sales;
import com.poncheck.entity.User;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.repository.ProductRepository;
import com.poncheck.repository.SalesRepository;
import com.poncheck.repository.UserRepository;
import com.poncheck.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final SalesRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

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

        BigDecimal total = BigDecimal.ZERO;

        Sales sale = new Sales(
                total,
                data.paymentMethod(),
                data.description(),
                user
        );

        List<SaleItemRequestDTO> items = data.items();

        for(SaleItemRequestDTO item : items) {
            Product product = productRepository.findById(item.productId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
            BigDecimal unitPrice = product.getPrice();
            Integer quantity = item.quantity();
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
            total = total.add(subtotal);

            SaleItem saleItem = new SaleItem(
                    quantity,
                    unitPrice,
                    subtotal,
                    product
            );

            sale.addSaleItem(saleItem);
        }
        sale.setTotal(total);
        Sales saleSaved = repository.save(sale);
        return new SalesResponseDTO(saleSaved);
    }
}
