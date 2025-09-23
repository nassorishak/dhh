//package com.example.decoration_backend_springboot.Model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "purchases")
//public class Purchase {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer purchaseId;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;
//
//    private Integer quantity;
//
//    private Double purchasePrice;
//
//    private String supplier;
//
//    @Temporal(TemporalType.DATE)
//    private Date purchaseDate;
//
//    public Integer getPurchaseId() {
//        return purchaseId;
//    }
//
//    public void setPurchaseId(Integer purchaseId) {
//        this.purchaseId = purchaseId;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public Double getPurchasePrice() {
//        return purchasePrice;
//    }
//
//    public void setPurchasePrice(Double purchasePrice) {
//        this.purchasePrice = purchasePrice;
//    }
//
//    public String getSupplier() {
//        return supplier;
//    }
//
//    public void setSupplier(String supplier) {
//        this.supplier = supplier;
//    }
//
//    public Date getPurchaseDate() {
//        return purchaseDate;
//    }
//
//    public void setPurchaseDate(Date purchaseDate) {
//        this.purchaseDate = purchaseDate;
//    }
//}
//
//


package com.example.decoration_backend_springboot.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseId; // Use Integer, not int

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity; // Use Integer, not int

    private Double purchasePrice; // Use Double, not double

    private String supplier;

    @Temporal(TemporalType.DATE)
    private Date purchaseDate;

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    // Getters and setters...
}