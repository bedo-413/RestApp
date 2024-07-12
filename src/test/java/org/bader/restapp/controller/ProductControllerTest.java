package org.bader.restapp.controller;

import org.bader.restapp.RestAppApplication;
import org.bader.restapp.model.Products;
import org.bader.restapp.repositories.ProductRepositories;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test")
@SpringBootTest//(classes = RestAppApplication.class)
@AutoConfigureMockMvc
class ProductsControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepositories productRepositories;

    @BeforeEach
    void setUp() {
        productRepositories.deleteAll();
        productRepositories.save(new Products(1L, "Product1", "Description1", 100.0));
        productRepositories.save(new Products(2L, "Product2", "Description2", 200.0));
        productRepositories.findAll().forEach(System.out::println);
    }

    @Test
    public void whenGetAllProducts_thenReturnAllProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].name", is("Product1")))
                .andExpect(jsonPath("$[1].name", is("Product2")));
    }

    @Test
    public void whenGetProductById_thenReturnProduct() throws Exception {
        //get the id of the product you want to test
        Long productId = productRepositories.findAll().get(0).getId();

        mockMvc.perform(get("/products" + "/" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Product1")))
                .andExpect(jsonPath("$.description", is("Description1")));
    }

    @Test
    public void whenCreateProduct_thenReturnCreatedProduct() throws Exception {
        String newProductJson = "{\"name\":\"Product3\",\"description\":\"Description3\",\"price\":300.0}";

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Product3")))
                .andExpect(jsonPath("$.description", is("Description3")));
    }

    @Test
    public void whenUpdateProduct_thenReturnUpdatedProduct() throws Exception {
        Long productId = productRepositories.findAll().get(0).getId();

        String updatedProductJson = "{\"name\":\"UpdatedProduct1\",\"description\":\"UpdatedDescription1\",\"price\":150.0}";

        mockMvc.perform(put("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("UpdatedProduct1")))
                .andExpect(jsonPath("$.description", is("UpdatedDescription1")));
    }

    @Test
    public void whenDeleteProduct_thenReturnNoContent() throws Exception {
        Long productId = productRepositories.findAll().get(0).getId();

        mockMvc.perform(delete("/products/" + productId))
                .andExpect(status().isNoContent());

        Optional<Products> deletedProduct = productRepositories.findById(productId);
        assertFalse(deletedProduct.isPresent());
    }

}