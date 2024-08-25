package com.example.decoration_backend_springboot.Service;
import com.example.decoration_backend_springboot.Model.Product;
import com.example.decoration_backend_springboot.Model.dto.ProductDTO;
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

    public Optional<Product> findById(int productId) {
        return productRepository.findById(productId);
    }

    public List<Product> findAll() {
        return  productRepository.findAll();
    }


    public void deleteById(int productId) {
        productRepository.deleteById(productId);
    }

    public List<Product> searchProducts(String query) {
        if (query == null || query.trim().isEmpty()) {
            return productRepository.findAll();
        }
        return productRepository.findByProductNameContainingIgnoreCaseOrProductCompanyContainingIgnoreCase(query, query);
    }


}