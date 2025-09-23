package com.example.decoration_backend_springboot.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stockId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer findSumOfInventoryValue() {
        return null;
    }

    private Integer inStock;

    private Integer outStock;

    private Integer currentStock;

    @PrePersist
    @PreUpdate
    public void calculateCurrentStock() {
        if (inStock == null) inStock = 0;
        if (outStock == null) outStock = 0;
        currentStock = inStock - outStock;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Integer getOutStock() {
        return outStock;
    }

    public void setOutStock(Integer outStock) {
        this.outStock = outStock;
    }

    public Integer getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
    }
}

