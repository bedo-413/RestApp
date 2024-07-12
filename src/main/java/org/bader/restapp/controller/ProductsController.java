package org.bader.restapp.controller;


import org.bader.restapp.model.Products;
import org.bader.restapp.service.ProductService;
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
    private ProductService productService;
    @Autowired
    private ReturnTypeParser genericReturnTypeParser;

    @GetMapping
    public List<Products> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional getProductById(@PathVariable Long id) {
        return productService.getProductsByID(id);
    }

    @PostMapping
    public Products createProduct(@RequestBody Products products) {
        return productService.saveProducts(products);
    }

    @PutMapping("/{id}")
    public Products updateProduct(@PathVariable Long id, @RequestBody Products products) {
        return productService.updateProducts(products, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        /*if (productService.getProductsByID(id).isPresent()) {
            productService.deleteProductsByID(id);
            return ResponseEntity.ok().build();
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return null;*/

        productService.deleteProductsByID(id);
        return ResponseEntity.noContent().build();
    }

}
