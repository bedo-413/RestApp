package org.bader.restapp.service;

import org.bader.restapp.model.Products;

import java.util.List;
import java.util.Optional;

public interface ProductsService {

    Products saveProducts(Products products);
    Optional getProductsByID(Long id);
    public List<Products> getAllProducts();
    Products updateProducts(Products products, Long id);
    void deleteProductsByID(Long id);

}
