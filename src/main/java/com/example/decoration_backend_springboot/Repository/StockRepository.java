package com.example.decoration_backend_springboot.Repository;

import com.example.decoration_backend_springboot.Model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
}
