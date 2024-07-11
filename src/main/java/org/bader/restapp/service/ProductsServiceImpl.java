package org.bader.restapp.service;

import org.bader.restapp.model.Products;
import org.bader.restapp.repositories.ProductRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    ProductRepositories productRepositories;


    @Override
    public Products saveProducts(Products products) {
        return productRepositories.save(products);
    }

    @Override
    public Optional getProductsByID(Long id) {
        return productRepositories.findById(id);
    }

    @Override
    public List<Products> getAllProducts() {
        return productRepositories.findAll();
    }

    @Override
    public Products updateProducts(Products products, Long id) {
        Optional<Products> productsToUpdate = productRepositories.findById(id);
        if (productsToUpdate.isPresent()) {
            Products products1 = productsToUpdate.get();

            products1.setName(products.getName());
            products1.setPrice(products.getPrice());
            products1.setDescription(products.getDescription());

            return (Products) productRepositories.save(products1);
        }
        return null;
    }

    @Override
    public void deleteProductsByID(Long id) {
        /*if(productRepositories.existsById(id)) {
            productRepositories.deleteById(id);
            return;
        }
        else {
            System.out.println("Product not found" + id);
            throw new RuntimeException();
        }*/
        productRepositories.deleteById(id);
    }
}
