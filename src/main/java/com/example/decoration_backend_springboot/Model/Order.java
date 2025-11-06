package com.example.decoration_backend_springboot.Model;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name = "orders")
@Data
public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int orderId;

        @Temporal(TemporalType.DATE)
        private Date date;

        private String status;
        private String controlNumber;
        private String quantity;
        private String orderType;
        private String size;

        // Payment fields
        private String paymentStatus;
        private BigDecimal totalAmount;
        private String customerEmail;
        private String customerPhone;
        private String customerName; // ADD THIS FIELD
        private String preferredNetwork; // ADD THIS FIELD

        @ManyToOne
        @JoinColumn(name="customerId")
        private Customer customer;

        @ManyToOne
        @JoinColumn(name = "productId")
        private Product product;

        @CreationTimestamp
        private LocalDateTime createdAt;

        // Constructors
        public Order() {
                this.date = new Date();
                this.status = "PENDING";
                this.paymentStatus = "PENDING";
                this.controlNumber = generateControlNumber();
        }

        // Helper method to generate control number
        private String generateControlNumber() {
                return "2025" + System.currentTimeMillis() + (int)(Math.random() * 1000);
        }

        // Getters and Setters
        public String getCustomerName() {
                return customerName;
        }

        public void setCustomerName(String customerName) {
                this.customerName = customerName;
        }

        public String getPreferredNetwork() {
                return preferredNetwork;
        }

        public void setPreferredNetwork(String preferredNetwork) {
                this.preferredNetwork = preferredNetwork;
        }

        public void setOrderDate(LocalDateTime now) {
        }

        public int getOrderId() {
                return orderId;
        }

        public void setOrderId(int orderId) {
                this.orderId = orderId;
        }

        public Date getDate() {
                return date;
        }

        public void setDate(Date date) {
                this.date = date;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public String getControlNumber() {
                return controlNumber;
        }

        public void setControlNumber(String controlNumber) {
                this.controlNumber = controlNumber;
        }

        public String getQuantity() {
                return quantity;
        }

        public void setQuantity(String quantity) {
                this.quantity = quantity;
        }

        public String getOrderType() {
                return orderType;
        }

        public void setOrderType(String orderType) {
                this.orderType = orderType;
        }

        public String getSize() {
                return size;
        }

        public void setSize(String size) {
                this.size = size;
        }

        public String getPaymentStatus() {
                return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
                this.paymentStatus = paymentStatus;
        }

        public BigDecimal getTotalAmount() {
                return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
                this.totalAmount = totalAmount;
        }

        public String getCustomerEmail() {
                return customerEmail;
        }

        public void setCustomerEmail(String customerEmail) {
                this.customerEmail = customerEmail;
        }

        public String getCustomerPhone() {
                return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
                this.customerPhone = customerPhone;
        }

        public Customer getCustomer() {
                return customer;
        }

        public void setCustomer(Customer customer) {
                this.customer = customer;
        }

        public Product getProduct() {
                return product;
        }

        public void setProduct(Product product) {
                this.product = product;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }
}