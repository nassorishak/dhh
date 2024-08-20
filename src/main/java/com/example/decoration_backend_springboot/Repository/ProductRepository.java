package com.example.decoration_backend_springboot.Repository;
import com.example.decoration_backend_springboot.Model.Product;
import com.example.decoration_backend_springboot.Model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByProductNameContainingIgnoreCaseOrProductCompanyContainingIgnoreCase(String productName, String productCompany);
}
