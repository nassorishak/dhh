package com.example.decoration_backend_springboot.Service;
import com.example.decoration_backend_springboot.Model.Product;
import com.example.decoration_backend_springboot.Repository.PaymentRepository;
import com.example.decoration_backend_springboot.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findById(int product_id) {
        return productRepository.findById(product_id);
    }

    public List<Product> findAll() {
        return  productRepository.findAll();
    }


    public void deleteById(int product_id) {
        productRepository.deleteById(product_id);
    }
}