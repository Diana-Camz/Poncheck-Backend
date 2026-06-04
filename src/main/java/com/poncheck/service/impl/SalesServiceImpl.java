package com.poncheck.service.impl;

import com.poncheck.dto.request.sales.CancelSaleRequestDTO;
import com.poncheck.dto.request.sales.CreateSaleRequestDTO;
import com.poncheck.dto.request.sales.SaleItemRequestDTO;
import com.poncheck.dto.request.sales.UpdateSaleRequestDTO;
import com.poncheck.dto.response.sales.SalesResponseDTO;
import com.poncheck.entity.*;
import com.poncheck.enums.*;
import com.poncheck.exception.*;
import com.poncheck.repository.*;
import com.poncheck.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final SalesRepository repository;
    private final CashRegisterRepository registerRepository;
    private final ProductRepository productRepository;
    private final SaleItemRepository saleItemRepository;
    private final MovementRepository movementRepository;
    private final CashMovementRepository cashMovementRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final BusinessContextService businessContextService;

    @Override
    public List<SalesResponseDTO> getAllSales(){
        List<Sales> salesList = repository.findAll();
       return salesList.stream()
               .map(SalesResponseDTO::new)
               .toList();
    }

    @Override
    public List<SalesResponseDTO> getSalesByStatus(SaleStatus status) {
        User currentUser = authenticatedUserService.getCurrentUser();
        List<Sales> sales;
        if(currentUser.getRole() == Role.ADMIN){
            sales = repository.findBySaleStatus(status);
        }else{
            sales = repository.findBySaleStatusAndBusinessId(status, currentUser.getBusiness().getId());
        }

        return sales.stream()
                .map(SalesResponseDTO::new)
                .toList();
    }

    @Override
    public SalesResponseDTO getSaleById(Long saleId) {
        Long businessId = businessContextService.getCurrentBusiness().getId();
        Sales sale = repository.findByIdAndBusiness_id(saleId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale Not Found", "sale", saleId));
        return new SalesResponseDTO(sale);
    }

    @Override
    public List<SalesResponseDTO> getSalesByDateRange(LocalDateTime start, LocalDateTime end){
        if (start.isAfter(end)) {
            throw new InvalidDateRangeException("Start date cannot be after end date");
        }
        Long businessId = businessContextService.getCurrentBusiness().getId();
        List<Sales> salesList = repository.findByDateBetweenAndBusinessId(start, end, businessId);
        return salesList.stream().map(SalesResponseDTO::new).toList();
    }

    @Transactional
    @Override
    public SalesResponseDTO createSale(CreateSaleRequestDTO data){

        Business business = businessContextService.getBusiness(data.businessId());
        CashRegister register = registerRepository
                .findByStatusAndBusiness_id(CashRegisterStatus.OPEN, business.getId())
                .orElseThrow(() -> new InvalidCashRegisterException("Cash Register is not open yet"));
        User user = authenticatedUserService.getCurrentUser();
        BigDecimal total = BigDecimal.ZERO;

        Sales sale = new Sales(
                total,
                data.paymentMethod(),
                data.description(),
                user,
                register,
                business
        );

        if (data.items() == null || data.items().isEmpty()) {
            throw new InvalidSaleException("Sale must have at least one item");
        }
        List<SaleItemRequestDTO> items = data.items();
         for(SaleItemRequestDTO item : items) {
            Product product = productRepository.findByIdAndBusiness_id(item.productId(), business.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product Not Found", "product", item.productId()));
            if(!product.getActive()){
                throw new ResourceDisabledException("Product is disabled", product.getId());
            }
            if(!product.getCategory().getActive()){
                throw new ResourceDisabledException("Category is disabled", product.getCategory().getId());
            }
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
            Movement movement = new Movement(
                     TypeInventoryMovement.SALE,
                     quantity,
                     data.description(),
                     user,
                     product,
                     sale,
                     null,
                    business
             );
            movementRepository.save(movement);
            product.decreaseStock(quantity);
         };


        register.increaseExpectedAmount(total);
        registerRepository.save(register);
        sale.setTotal(total);
        Sales saleSaved = repository.save(sale);
        CashMovement cashMovement = new CashMovement(
            TypeCashMovement.SALE,
            total,
            user,
            saleSaved,
            null,
            register,
            data.description(),
            business
        );
        cashMovementRepository.save(cashMovement);
        return new SalesResponseDTO(saleSaved);
    }

    @Override
    public SalesResponseDTO updateSale(Long saleId, UpdateSaleRequestDTO data){
        Long businessId = businessContextService.getBusiness(data.businessId()).getId();
        Sales sale = repository.findByIdAndBusiness_id(saleId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale Not Found", "sale", saleId));

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
        Business business = businessContextService.getBusiness(data.businessId());
        CashRegister register = registerRepository
                .findByStatusAndBusiness_id(CashRegisterStatus.OPEN, business.getId())
                .orElseThrow(() -> new InvalidCashRegisterException("Cash Register is not open yet"));
        Sales sale = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale Not Found", "sale", id));

        User user = authenticatedUserService.getCurrentUser();


        sale.cancelSale(
                user,
                business,
                data.reason()
        );
        Sales saleCancelled = repository.save(sale);

        List<SaleItem> itemsCancelled = saleItemRepository.findAllBySale_id(id);
        for(SaleItem item : itemsCancelled){
            Product product = item.getProduct();
            Movement movement = new Movement(
                TypeInventoryMovement.SALE_CANCELLED,
                    item.getQuantity(),
                    data.reason(),
                    user,
                    product,
                    sale,
                    null,
                    business
            );
            movementRepository.save(movement);
            product.increaseStock(item.getQuantity());
        }
        register.decreaseExpectedAmount(saleCancelled.getTotal());
        registerRepository.save(register);

        CashMovement cashMovement = new CashMovement(
                TypeCashMovement.SALE_CANCELLED,
                saleCancelled.getTotal(),
                user,
                saleCancelled,
                saleCancelled.getCancelled(),
                register,
                data.reason(),
                business
        );

        cashMovementRepository.save(cashMovement);
    }
}
