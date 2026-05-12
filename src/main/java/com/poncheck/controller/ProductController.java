package com.poncheck.controller;

import com.poncheck.dto.request.product.CreateProductRequestDTO;
import com.poncheck.dto.request.product.UpdateActiveProductRequestDTO;
import com.poncheck.dto.request.product.UpdateProductRequestDTO;
import com.poncheck.dto.response.ProductResponseDTO;
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


    //Retrieves a list of all products
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts(){
        List<ProductResponseDTO> productsDto = service.getProducts();
        return ResponseEntity.ok(productsDto);
    }

    //Retrieves a product by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
        ProductResponseDTO product = service.getProductById(id);
        return ResponseEntity.ok(product);
    }

    //Creates a new product, categoryID must not be null
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid CreateProductRequestDTO productData){
        ProductResponseDTO product = service.createProduct(productData);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    //Updates product fields by its ID
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody @Valid UpdateProductRequestDTO productData){
        ProductResponseDTO product = service.updateProduct(id, productData);
        return ResponseEntity.ok(product);
    }

    //Updates the product active status (logical deletion)
    @PatchMapping("/{id}/active")
    public ResponseEntity<ProductResponseDTO> updateActive(@PathVariable Long id, @RequestBody @Valid UpdateActiveProductRequestDTO status){
        ProductResponseDTO product = service.updateActive(id, status);
        return ResponseEntity.ok(product);
    }

    //Deletes a product (physical deletion)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @Valid Long id){
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
