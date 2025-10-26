package com.example.decoration_backend_springboot.Repository;

import com.example.decoration_backend_springboot.Model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    // Fixed: Now using the correct stock field that exists in Sale entity
    @Query("SELECT COALESCE(SUM(s.quantity), 0) FROM Sale s WHERE s.stock.stockId = :stockId")
    Integer sumQuantitySoldByStockId(@Param("stockId") Integer stockId);

    // Additional query if you need to sum by product ID as well
    @Query("SELECT COALESCE(SUM(s.quantity), 0) FROM Sale s WHERE s.product.productId = :productId")
    Integer sumQuantitySoldByProductId(@Param("productId") Integer productId);
}