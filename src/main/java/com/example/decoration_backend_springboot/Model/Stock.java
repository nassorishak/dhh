package com.example.decoration_backend_springboot.Model;////package com.example.decoration_backend_springboot.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    private Integer inStock;
    private Integer outStock;
    private Integer currentStock;

    private String status; // "In Stock", "Partially Sold", "Sold Out"
    private Double latestPurchasePrice;
    private Double sellingPrice;


    @ManyToOne
    @JoinColumn(name = "shelf_id")
    private Shelf shelf;

    // ===== Calculate current stock before saving/updating =====
    @PrePersist
    @PreUpdate
    public void calculateCurrentStock() {
        if (inStock == null) inStock = 0;
        if (outStock == null) outStock = 0;
        currentStock = inStock - outStock;

        // Automatically update status
        if (currentStock == 0) {
            status = "Sold Out";
        } else if (outStock > 0) {
            status = "Partially Sold";
        } else {
            status = "In Stock";
        }
    }

    // ===== Profit Calculation Method =====
    @Transient // This field won't be persisted in database
    public Double getProfit() {
        if (latestPurchasePrice == null || sellingPrice == null || outStock == null) {
            return 0.0;
        }
        return (sellingPrice - latestPurchasePrice) * outStock;
    }

    // ===== Getters and Setters =====
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
        calculateCurrentStock(); // Recalculate when inStock changes
    }

    public Integer getOutStock() {
        return outStock;
    }

    public void setOutStock(Integer outStock) {
        this.outStock = outStock;
        calculateCurrentStock(); // Recalculate when outStock changes
    }

    public Integer getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLatestPurchasePrice() {
        return latestPurchasePrice;
    }

    public void setLatestPurchasePrice(Double latestPurchasePrice) {
        this.latestPurchasePrice = latestPurchasePrice;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}