package com.poncheck.service.impl;

import com.poncheck.dto.request.product.CreateProductRequestDTO;
import com.poncheck.dto.request.product.UpdateActiveProductRequestDTO;
import com.poncheck.dto.request.product.UpdateProductRequestDTO;
import com.poncheck.dto.response.product.ProductResponseDTO;
import com.poncheck.entity.Category;
import com.poncheck.entity.Product;
import com.poncheck.exception.DuplicateFieldException;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.repository.ProductRepository;
import com.poncheck.repository.CategoryRepository;
import com.poncheck.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    //Retrieves a product by its ID
    @Override
    public ProductResponseDTO getProductById(Long productId) {
        Product product = repository.findById(productId)
               .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
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
        Category category = categoryRepository.findById(productData.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category ID Not Found"));
        String code = generateProductCode(category);
        Product product = new Product(
                productData.name(),
                productData.price(),
                productData.flavor(),
                productData.description(),
                productData.productSize(),
                productData.poncheBase(),
                category,
                code);

        Product savedProduct = repository.save(product);
        return new ProductResponseDTO(savedProduct);
    }

    //Updates product fields by its ID
    @Override
    public ProductResponseDTO updateProduct(Long id, UpdateProductRequestDTO data) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
        if(repository.existsByCode(data.code())){
                throw new DuplicateFieldException("A product with this code already exists");
        }
        Category category = null;
        if(data.categoryId() != null){
            category = categoryRepository.findById(data.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));
        }

        product.updateData(
                data.name(),
                data.code(),
                data.price(),
                data.flavor(),
                data.poncheBase(),
                data.productSize(),
                category);
        Product updatedProduct = repository.save(product);
        return new ProductResponseDTO(updatedProduct);
    }

    //Updates the product active status (logical deletion)
    @Override
    public ProductResponseDTO updateActive(Long id, UpdateActiveProductRequestDTO status){
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        product.updateActive(status.active());

        Product updatedStatus = repository.save(product);
        return new ProductResponseDTO(updatedStatus);
    }
    //Deletes a product (physical deletion)
    @Override
    public void deleteProduct(Long id){
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
        repository.delete(product);
    }

}
