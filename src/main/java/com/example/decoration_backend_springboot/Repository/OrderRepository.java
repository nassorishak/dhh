package com.example.decoration_backend_springboot.Repository;

import com.example.decoration_backend_springboot.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomer_UserId(int userId);

    // Find order by orderId
    Order findByOrderId(int orderId);

    // Delete order by orderId
    void deleteByOrderId(int orderId);

    // Custom method to find an order by orderId and controlNumber
    Order findByOrderIdAndControlNumber(int orderId, String controlNumber);

}
