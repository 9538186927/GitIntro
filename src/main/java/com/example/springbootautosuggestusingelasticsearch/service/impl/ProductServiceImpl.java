package com.example.springbootautosuggestusingelasticsearch.service.impl;

import com.example.springbootautosuggestusingelasticsearch.entity.Product;
import com.example.springbootautosuggestusingelasticsearch.repo.ProductRepo;
import com.example.springbootautosuggestusingelasticsearch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

@Service
public class ProductServiceImpl implements ProductService {

@Autowired
private ProductRepo    productRepo;
    @Override
    public Product insertProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Iterable<Product> getProduct() {
        return productRepo.findAll();
    }

    @Override
    public Product deleteProduct(Integer id) {
        Product product = productRepo.findById(id).orElse(null);
        if (product != null) {
            productRepo.deleteById(id);
        }
        return product;
    }
}
