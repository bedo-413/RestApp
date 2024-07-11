package org.bader.restapp.controller;


import org.bader.restapp.model.Products;
import org.bader.restapp.service.ProductsService;
import org.springdoc.core.parsers.ReturnTypeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;
    @Autowired
    private ReturnTypeParser genericReturnTypeParser;

    @GetMapping
    public List<Products> getAllProducts() {
        return productsService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional getProductById(@PathVariable Long id) {
        return productsService.getProductsByID(id);
    }

    @PostMapping
    public Products createProduct(@RequestBody Products products) {
        return productsService.saveProducts(products);
    }

    @PutMapping("/{id}")
    public Products updateProduct(@PathVariable Long id, @RequestBody Products products) {
        return productsService.updateProducts(products, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productsService.getProductsByID(id).isPresent()) {
            productsService.deleteProductsByID(id);
            return ResponseEntity.ok().build();
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return null;
    }

}
