//package com.example.decoration_backend_springboot.Model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Table(name = "products")
//@Data
//public class Product {
//
//
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private int productId;
//
//        private String productName;
//
//        private String productDescription;
//
//        private Double price;
//        private  String productCompany;
//
//        @Lob
//        @Column(length = 1000000)
//        private byte[] image;
//
//        private String category;
//
//
//
//
//
//}
package com.example.decoration_backend_springboot.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer productId;

        private String productCode;   // ✅ Added product code

        private String productName;

        private String productDescription;

        private Double price;

        private String productCompany;

        private String productUnit;   // ✅ Added product unit (e.g., piece, kg, meter)

        private Integer stockQuantity; // ✅ Added stock quantity

        @Lob
        @Column(length = 1000000)
        private byte[] image;

        private String category;

        // ✅ Many-to-One relationship with User
        @ManyToOne
        @JoinColumn(name = "userId", referencedColumnName = "userId")
        private User user; // ✅ Use the entity, not Integer


        public Integer getProductId() {
                return productId;
        }

        public void setProductId(Integer productId) {
                this.productId = productId;
        }

        public String getProductCode() {
                return productCode;
        }

        public void setProductCode(String productCode) {
                this.productCode = productCode;
        }

        public String getProductName() {
                return productName;
        }

        public void setProductName(String productName) {
                this.productName = productName;
        }

        public String getProductDescription() {
                return productDescription;
        }

        public void setProductDescription(String productDescription) {
                this.productDescription = productDescription;
        }

        public Double getPrice() {
                return price;
        }

        public void setPrice(Double price) {
                this.price = price;
        }

        public String getProductCompany() {
                return productCompany;
        }

        public void setProductCompany(String productCompany) {
                this.productCompany = productCompany;
        }

        public String getProductUnit() {
                return productUnit;
        }

        public void setProductUnit(String productUnit) {
                this.productUnit = productUnit;
        }

        public Integer getStockQuantity() {
                return stockQuantity;
        }

        public void setStockQuantity(Integer stockQuantity) {
                this.stockQuantity = stockQuantity;
        }

        public byte[] getImage() {
                return image;
        }

        public void setImage(byte[] image) {
                this.image = image;
        }

        public String getCategory() {
                return category;
        }

        public void setCategory(String category) {
                this.category = category;
        }


        public User getUser() {
                return user;
        }

        public void setUser(User user) {
                this.user = user;
        }
}
