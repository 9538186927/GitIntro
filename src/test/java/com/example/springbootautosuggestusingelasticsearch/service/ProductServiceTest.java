package com.example.springbootautosuggestusingelasticsearch.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.springbootautosuggestusingelasticsearch.entity.Product;
import com.example.springbootautosuggestusingelasticsearch.repo.ProductRepo;
import com.example.springbootautosuggestusingelasticsearch.service.impl.ProductServiceImpl;

class ProductServiceTest {

    @Mock
    ProductRepo productRepo;


    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testInsertProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Test Product");

        when(productRepo.save(any(Product.class))).thenReturn(product);

        Product result = productService.insertProduct(product);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Product", result.getName());

        verify(productRepo, times(1)).save(product);
    }

    @Test
    public void testGetProduct() {
        List<Product> products = Arrays.asList(
                new Product(1, "Product1", 1233, 12),
                new Product(2, "Product2",12345, 10)
        );
        when(productRepo.findAll()).thenReturn(products);

        Iterable<Product> result = productService.getProduct();
        assertNotNull(result);
        assertEquals(2, ((Collection<?>) result).size());

        verify(productRepo, times(1)).findAll();
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Test Product");

        when(productRepo.findById(anyInt())).thenReturn(Optional.of(product));
        doNothing().when(productRepo).deleteById(anyInt());

        Product result = productService.deleteProduct(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Product", result.getName());

        verify(productRepo, times(1)).findById(1);
        verify(productRepo, times(1)).deleteById(1);
    }
}