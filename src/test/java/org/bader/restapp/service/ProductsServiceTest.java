package org.bader.restapp.service;

import org.bader.restapp.model.Products;
import org.bader.restapp.repositories.ProductRepositories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductsServiceTest {

    @Mock
    private ProductRepositories productRepositories;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        productService.getAllProducts();
        verify(productRepositories, times(1)).findAll();
    }

    @Test
    public void testGetProductById() {
        Long id = 1L;
        Products product = new Products(id, "Product1", "Description1", 100.0);
        when(productRepositories.findById(id)).thenReturn(Optional.of(product));

        Optional<Products> result = productService.getProductsByID(id);
        assertEquals(product.getName(), result.get().getName());
        verify(productRepositories, times(1)).findById(id);
    }

    @Test
    public void testCreateProduct() {
        Products product = new Products(null, "Product1", "Description1", 100.0);
        when(productRepositories.save(product)).thenReturn(product);

        Products result = productService.saveProducts(product);
        assertEquals(product.getName(), result.getName());
        verify(productRepositories, times(1)).save(product);
    }

    @Test
    public void testUpdateProduct() {
        Long id = 1L;
        Products existingProduct = new Products(id, "Product1", "Description1", 100.0);
        Products updatedProduct = new Products(id, "UpdatedProduct1", "UpdatedDescription1", 150.0);

        when(productRepositories.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepositories.save(existingProduct)).thenReturn(updatedProduct);

        Products result = productService.updateProducts(updatedProduct, id);
        assertEquals(updatedProduct.getName(), result.getName());
        verify(productRepositories, times(1)).findById(id);
        verify(productRepositories, times(1)).save(existingProduct);
    }

    @Test
    public void testDeleteProduct() {
        Long id = 1L;
        productService.deleteProductsByID(id);
        verify(productRepositories, times(1)).deleteById(id);
    }

}
