package com.poncheck.service;

import com.poncheck.dto.request.product.CreateProductRequestDTO;
import com.poncheck.dto.request.product.UpdateActiveProductRequestDTO;
import com.poncheck.dto.request.product.UpdateProductRequestDTO;
import com.poncheck.dto.response.product.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO getProductById(Long productId);
    List<ProductResponseDTO> getProducts();
    List<ProductResponseDTO> getActiveProducts();
    List<ProductResponseDTO> getInactiveProducts();
    ProductResponseDTO createProduct(CreateProductRequestDTO productData);
    ProductResponseDTO updateProduct(Long id, UpdateProductRequestDTO productData);
    ProductResponseDTO updateActive(Long id, UpdateActiveProductRequestDTO status);
    void deleteProduct(Long id);
}
