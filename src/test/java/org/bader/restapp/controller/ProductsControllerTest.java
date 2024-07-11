package org.bader.restapp.controller;

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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/data.sql")
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
        mockMvc.perform(get("/products/1"))
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
        String updatedProductJson = "{\"name\":\"UpdatedProduct1\",\"description\":\"UpdatedDescription1\",\"price\":150.0}";

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("UpdatedProduct1")))
                .andExpect(jsonPath("$.description", is("UpdatedDescription1")));
    }

    @Test
    public void whenDeleteProduct_thenReturnNoContent() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }

}