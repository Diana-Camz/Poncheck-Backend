package com.poncheck.service.impl;

import com.poncheck.dto.request.ProductRequestDTO;
import com.poncheck.dto.response.ProductResponseDTO;
import com.poncheck.entity.Category;
import com.poncheck.entity.Product;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.repository.ProductRepository;
import com.poncheck.repository.CategoryRepository;
import com.poncheck.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponseDTO getProductById(Long productId) {
        Product product = repository.findById(productId)
               .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return new ProductResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getProducts(){
        List<Product> products = repository.findAll();
        return products.stream()
                .map(ProductResponseDTO::new)
                .toList();
    }

    //Metodo que genera automaticamente un codigo al crear un nuevo producto, basandose en el nombre de la categoria y un numero secuencial.
    private String generateProductCode(Category category){
        String prefix = category.getName()
                .substring(0,3)
                .toUpperCase();

        long totalProducts = repository.countByCategoryId(category.getId());
        return prefix + String.format("%03d", totalProducts + 1);
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productData){
        Category category = categoryRepository.findById(productData.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category ID Not Found"));
        String code = generateProductCode(category);
        Product product = new Product(productData, category, code);

        Product savedProduct = repository.save(product);
        return new ProductResponseDTO(savedProduct);
    }
}
