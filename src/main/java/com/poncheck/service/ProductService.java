package com.poncheck.service;

import com.poncheck.dto.request.ProductRequestDTO;
import com.poncheck.dto.response.ProductResponseDTO;
import com.poncheck.entity.Category;
import com.poncheck.entity.Product;

import java.util.List;

public interface ProductService {
    ProductResponseDTO getProductById(Long productId);
    List<ProductResponseDTO> getProducts();
    ProductResponseDTO createProduct(ProductRequestDTO productData);
}
