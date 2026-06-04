package com.poncheck.service.impl;

import com.poncheck.dto.request.product.CreateProductRequestDTO;
import com.poncheck.dto.request.product.UpdateActiveProductRequestDTO;
import com.poncheck.dto.request.product.UpdateProductRequestDTO;
import com.poncheck.dto.response.product.ProductResponseDTO;
import com.poncheck.entity.Business;
import com.poncheck.entity.Category;
import com.poncheck.entity.Product;
import com.poncheck.entity.User;
import com.poncheck.enums.Role;
import com.poncheck.exception.DuplicateFieldException;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.repository.ProductRepository;
import com.poncheck.repository.CategoryRepository;
import com.poncheck.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final BusinessContextService businessContextService;
    private final AuthenticatedUserService authenticatedUserService;

    //Retrieves a product by its ID
    @Override
    public ProductResponseDTO getProductById(Long productId) {
        Long businessId = businessContextService.getCurrentBusiness().getId();
        Product product = repository.findByIdAndBusiness_id(productId, businessId)
               .orElseThrow(() -> new ResourceNotFoundException("Product Not Found", "product", productId));
        return new ProductResponseDTO(product);
    }

    //Retrieves a list of all products
    @Override
    public List<ProductResponseDTO> getProducts(){
        List<Product> products = repository.findAll();
        return products.stream()
                .map(ProductResponseDTO::new)
                .toList();
    }

    //Retrieves a list of all active products
    @Override
    public List<ProductResponseDTO> getActiveProducts(){
        User currentUser = authenticatedUserService.getCurrentUser();
        List<Product> products;
        if(currentUser.getRole() == Role.ADMIN){
            products = repository.findByActiveTrue();
        }else{
            products = repository.findByActiveTrueAndBusinessId(currentUser.getBusiness().getId());
        }
        return products.stream()
                .map(ProductResponseDTO::new)
                .toList();
    }
    //Retrieves a list of all inactive products
    @Override
    public List<ProductResponseDTO> getInactiveProducts(){
        User currentUser = authenticatedUserService.getCurrentUser();
        List<Product> products;
        if(currentUser.getRole() == Role.ADMIN){
            products = repository.findByActiveFalse();
        }else{
            products = repository.findByActiveFalseAndBusinessId(currentUser.getBusiness().getId());
        }
        return products.stream()
                .map(ProductResponseDTO::new)
                .toList();
    }

    //Method that automatically generates a code when creating a new product, based on the category
    // name and a sequential number
    private String generateProductCode(Category category){
        String prefix = category.getName()
                .substring(0,3)
                .toUpperCase();

        long totalProducts = repository.countByCategoryId(category.getId());
        return prefix + String.format("%03d", totalProducts + 1);
    }

    //Creates a new product, categoryID must not be null
    @Override
    public ProductResponseDTO createProduct(CreateProductRequestDTO productData){
        Long businessId = businessContextService.getBusiness(productData.businessId()).getId();
        Category category = categoryRepository.findByIdAndBusiness_id(productData.categoryId(), businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found", "category", productData.categoryId()));

        Business business = businessContextService.getBusiness(productData.businessId());
        String code = generateProductCode(category);
        Product product = new Product(
                productData.name(),
                productData.price(),
                productData.flavor(),
                productData.description(),
                productData.productSize(),
                productData.poncheBase(),
                category,
                code,
                business
        );

        Product savedProduct = repository.save(product);
        return new ProductResponseDTO(savedProduct);
    }

    //Updates product fields by its ID
    @Override
    public ProductResponseDTO updateProduct(Long productId, UpdateProductRequestDTO data) {
        Long businessId = businessContextService.getBusiness(data.businessId()).getId();
        Product product = repository.findByIdAndBusiness_id(productId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found", "product", productId));
        if(repository.existsByCodeAndBusinessId(data.code(), product.getBusiness().getId())){
                throw new DuplicateFieldException("A product with this code already exists");
        }
        Category category = null;
        if(data.categoryId() != null){
            category = categoryRepository.findByIdAndBusiness_id(data.categoryId(), businessId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category Not Found", "category", data.categoryId()));
        }

        product.updateData(
                data.name(),
                data.code(),
                data.price(),
                data.flavor(),
                data.description(),
                data.poncheBase(),
                data.productSize(),
                category);
        Product updatedProduct = repository.save(product);
        return new ProductResponseDTO(updatedProduct);
    }

    @Override
    public List<ProductResponseDTO> updateProductPrice(BigDecimal price) {
        return List.of();
    }

    //Updates the product active status (logical deletion)
    @Override
    public ProductResponseDTO updateActive(Long productId, UpdateActiveProductRequestDTO data){
        Long businessId = businessContextService.getBusiness(data.businessId()).getId();
        Product product = repository.findByIdAndBusiness_id(productId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found", "product", productId));

        product.updateActive(data.active());

        Product updatedStatus = repository.save(product);
        return new ProductResponseDTO(updatedStatus);
    }
    //Deletes a product (physical deletion)
    @Override
    public void deleteProduct(Long id){
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found", "product", id));
        repository.delete(product);
    }

}
