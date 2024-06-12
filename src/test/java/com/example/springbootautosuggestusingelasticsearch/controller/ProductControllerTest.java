package com.example.springbootautosuggestusingelasticsearch.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Arrays;

import com.example.springbootautosuggestusingelasticsearch.entity.Product;
import com.example.springbootautosuggestusingelasticsearch.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(productController).build();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        // Given
        Product product1 = new Product(1,"laptop", 1223, 14);
        Product product2 = new Product(2, "Mobile", 2000, 2);
        when(productService.getProduct()).thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/api/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("laptop"))
                .andExpect(jsonPath("$[0].price").value(1223))
                .andExpect(jsonPath("$[0].qty").value(14))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Mobile"))
                .andExpect(jsonPath("$[1].price").value(2000))
                .andExpect(jsonPath("$[1].qty").value(2));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // Given
        int productId = 1;
        Product deletedProduct = new Product(productId, "Laptop", 1000, 10);
        when(productService.deleteProduct(productId)).thenReturn(deletedProduct);

        // When & Then
        mockMvc.perform(delete("/api/delete/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(1000))
                .andExpect(jsonPath("$.qty").value(10));
    }

}