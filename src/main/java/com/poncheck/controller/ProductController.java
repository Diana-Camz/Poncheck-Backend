package com.poncheck.controller;

import com.poncheck.dto.request.product.CreateProductRequestDTO;
import com.poncheck.dto.request.product.UpdateActiveProductRequestDTO;
import com.poncheck.dto.request.product.UpdateProductPriceRequestDTO;
import com.poncheck.dto.request.product.UpdateProductRequestDTO;
import com.poncheck.dto.response.product.ProductResponseDTO;
import com.poncheck.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products", description = "Endpoints for managing products, including creation, updates, price management, activation, deactivation, and deletion.")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;


    @Operation(summary = "Get all products", description = "Retrieves all products available for the current business.")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts(){
        List<ProductResponseDTO> productsDto = service.getProducts();
        return ResponseEntity.ok(productsDto);
    }

    @Operation(summary = "Get product by ID", description = "Retrieves a specific product by its identifier.")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
        ProductResponseDTO product = service.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Get active products", description = "Retrieves all active products for the current business.")
    @GetMapping("/active")
    public ResponseEntity<List<ProductResponseDTO>> getActiveProducts(){
        List<ProductResponseDTO> products = service.getActiveProducts();
        return ResponseEntity.ok(products);
    }
    @Operation(summary = "Get inactive products", description = "Retrieves all inactive products for the current business.")
    @GetMapping("/inactive")
    public ResponseEntity<List<ProductResponseDTO>> getInactiveProducts(){
        List<ProductResponseDTO> products = service.getInactiveProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Create product", description = "Creates a new product and associates it with a category and business.")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid CreateProductRequestDTO productData){
        ProductResponseDTO product = service.createProduct(productData);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @Operation(summary = "Update product", description = "Updates product information such as name, category, price, stock, size, or base.")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody @Valid UpdateProductRequestDTO productData){
        ProductResponseDTO product = service.updateProduct(id, productData);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Bulk update product prices", description = "Updates the price of multiple products using filters such as category, ponche base, and product size.")
    @PatchMapping("/prices")
    public ResponseEntity<List<ProductResponseDTO>> updateProductPrice(@RequestBody @Valid UpdateProductPriceRequestDTO productData){
        List<ProductResponseDTO> products = service.updateProductPrice(productData);
        return ResponseEntity.ok(products);
    }


    @Operation(summary = "Update product active status", description = "Activates or deactivates a product without permanently removing it from the system.")
    @PatchMapping("/{id}/active")
    public ResponseEntity<ProductResponseDTO> updateActive(@PathVariable Long id, @RequestBody @Valid UpdateActiveProductRequestDTO status){
        ProductResponseDTO product = service.updateActive(id, status);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Delete product", description = "Permanently removes a product from the system.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @Valid Long id){
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
