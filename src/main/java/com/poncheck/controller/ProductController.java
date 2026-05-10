package com.poncheck.controller;

import com.poncheck.dto.request.ProductRequestDTO;
import com.poncheck.dto.response.ProductResponseDTO;
import com.poncheck.entity.Product;
import com.poncheck.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts(){
        List<ProductResponseDTO> productsDto = service.getProducts();
        return ResponseEntity.ok(productsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
        ProductResponseDTO productDto = service.getProductById(id);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO productData){
        ProductResponseDTO producto = service.createProduct(productData);

        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }
}
