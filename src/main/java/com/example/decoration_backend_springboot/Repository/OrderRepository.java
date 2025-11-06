package com.example.decoration_backend_springboot.Repository;

import com.example.decoration_backend_springboot.Model.Order;
import com.example.decoration_backend_springboot.Model.Product;
import com.example.decoration_backend_springboot.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findByControlNumber(String controlNumber);

    List<Order> findByCustomerEmail(String customerEmail);

    List<Order> findByPaymentStatus(String paymentStatus);

    List<Order> findByStatus(String status);

    List<Order> findByProduct(Product product);

    List<Order> findByCustomer(Customer customer);


    // Search across multiple fields
    @Query("SELECT o FROM Order o WHERE " +
            "o.controlNumber LIKE %:searchTerm% OR " +
            "o.customerEmail LIKE %:searchTerm% OR " +
            "o.customer.name LIKE %:searchTerm% OR " +
            "o.product.productName LIKE %:searchTerm%")
    List<Order> findByControlNumberContainingOrCustomerEmailContainingOrCustomerNameContainingOrProductNameContaining(
            @Param("searchTerm") String searchTerm,
            @Param("searchTerm") String searchTerm2,
            @Param("searchTerm") String searchTerm3,
            @Param("searchTerm") String searchTerm4);

    // Get orders with pending payment
    @Query("SELECT o FROM Order o WHERE o.paymentStatus = 'PENDING' ORDER BY o.createdAt DESC")
    List<Order> findPendingPaymentOrders();

    // Get recent orders
    List<Order> findTop10ByOrderByCreatedAtDesc();

    // Count orders by status
    long countByStatus(String status);

    // Count orders by payment status
    long countByPaymentStatus(String paymentStatus);

    // Find orders by date range (you might need to add date field to Order entity)
    // List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}