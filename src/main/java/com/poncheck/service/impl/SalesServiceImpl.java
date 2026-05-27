package com.poncheck.service.impl;

import com.poncheck.dto.request.sales.CancelSaleRequestDTO;
import com.poncheck.dto.request.sales.CreateSaleRequestDTO;
import com.poncheck.dto.request.sales.SaleItemRequestDTO;
import com.poncheck.dto.request.sales.UpdateSaleRequestDTO;
import com.poncheck.dto.response.sales.SalesResponseDTO;
import com.poncheck.entity.*;
import com.poncheck.enums.SaleStatus;
import com.poncheck.enums.TypeMovement;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.repository.*;
import com.poncheck.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final SalesRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleItemRepository saleItemRepository;
    private final MovementRepository movementRepository;

    @Override
    public List<SalesResponseDTO> getAllSales(){
        List<Sales> salesList = repository.findAll();
       return salesList.stream()
               .map(SalesResponseDTO::new)
               .toList();
    }

    @Override
    public List<SalesResponseDTO> getSalesByStatus(SaleStatus status) {
        List<Sales> sales = repository.findBySaleStatus(status);
        return sales.stream()
                .map(SalesResponseDTO::new)
                .toList();
    }

    @Override
    public SalesResponseDTO getSaleById(Long id) {
        Sales sale = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale Not Found", "sale", id));
        return new SalesResponseDTO(sale);
    }

    @Transactional
    @Override
    public SalesResponseDTO createSale(CreateSaleRequestDTO data){
        User user = userRepository.findById(data.userId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        BigDecimal total = BigDecimal.ZERO;
        Integer quantity = 0;

        Sales sale = new Sales(
                total,
                data.paymentMethod(),
                data.description(),
                user
        );



        List<SaleItemRequestDTO> items = data.items();
         for(SaleItemRequestDTO item : items) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product Not Found", "product", item.productId()));
             BigDecimal unitPrice = product.getPrice();
            quantity = item.quantity();
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
          total = total.add(subtotal);

            SaleItem saleItem = new SaleItem(
                    quantity,
                    unitPrice,
                    subtotal,
                    product
            );

            sale.addSaleItem(saleItem);
             Movement movement = new Movement(
                     TypeMovement.SALE,
                     quantity,
                     data.description(),
                     user,
                     product,
                     sale,
                     null
             );
            movementRepository.save(movement);
        };

        sale.setTotal(total);
        Sales saleSaved = repository.save(sale);
        return new SalesResponseDTO(saleSaved);
    }

    @Override
    public SalesResponseDTO updateSale(Long id, UpdateSaleRequestDTO data){
        Sales sale = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale Not Found", "sale", id));

        sale.updateSale(
                data.paymentMethod(),
                data.description()
        );
        Sales saleSaved = repository.save(sale);
        return new SalesResponseDTO(saleSaved);
    }

    @Transactional
    @Override
    public void cancelSale(Long id, CancelSaleRequestDTO data) {
        Sales sale = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale Not Found", "sale", id));

        User user = userRepository.findById(data.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", "user", data.userId()));

        sale.cancelSale(
                id,
                user,
                data.reason()
        );

        List<SaleItem> itemsCancelled = saleItemRepository.findAllBySale_id(id);
        for(SaleItem item : itemsCancelled){
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow((() -> new ResourceNotFoundException("Product Not Found", "product", item.getProduct().getId())));
            Movement movement = new Movement(
                TypeMovement.SALE_CANCELLED,
                    item.getQuantity(),
                    data.reason(),
                    user,
                    product,
                    sale,
                    null
            );

            movementRepository.save(movement);
        }
        repository.save(sale);
    }
}
