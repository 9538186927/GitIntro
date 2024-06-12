package com.example.springbootautosuggestusingelasticsearch.repoIntergration;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.example.springbootautosuggestusingelasticsearch.entity.Product;
import com.example.springbootautosuggestusingelasticsearch.repo.ProductRepo;

@DataElasticsearchTest
@ActiveProfiles("test")

public class ProductRepoTest {

    @Autowired
    private ProductRepo productRepo;

    @BeforeEach
    public void setUp() {
        productRepo.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        productRepo.deleteAll();
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Test Product");

        Product savedProduct = productRepo.save(product);

        assertNotNull(savedProduct);
        assertEquals(1, savedProduct.getId());
        assertEquals("Test Product", savedProduct.getName());
    }

    @Test
    public void testFindAllProducts() {
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Product1");
        product1.setPrice(15677);
        product1.setQty(4);

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Product2");
        product2.setPrice(15677);
        product2.setQty(4);

        productRepo.save(product1);
        productRepo.save(product2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productsPage = productRepo.findAll(pageable);

        List<Product> products = productsPage.getContent();

        assertNotNull(products);
        assertEquals(2, products.size());
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Test Product");

        productRepo.save(product);
        productRepo.deleteById(1);

        Optional<Product> deletedProduct = productRepo.findById(1);
        assertFalse(deletedProduct.isPresent());
    }
}