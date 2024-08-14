package com.example.decoration_backend_springboot.Service;
import com.example.decoration_backend_springboot.Model.Order;
import com.example.decoration_backend_springboot.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    public List<Order> findAll() {
        return  orderRepository.findAll();
    }

    public Order save(Order order) {
        return  orderRepository.save(order);
    }

    public Optional<Order> findById(int orderId) {
        return  orderRepository.findById(orderId);
    }

    public void deleteById(int orderId) {
        orderRepository.deleteById(orderId);
    }
    public Long countAllOrders() {
        return orderRepository.count();
    }



    public List<Order> getOrdersByCustomerId(int userId) {
        return orderRepository.findByCustomer_UserId(userId);

    }

    public String cancelOrder(int orderId) {
        // Find the order by ID
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return "Order not found";
        }

        // Update the status to "canceled"
        order.setStatus("canceled");
        orderRepository.save(order);
        return "Order status updated to canceled";
    }

//    public String approveOrder(int orderId) {
//        // Find the order by ID
//        Order order = orderRepository.findById(orderId).orElse(null);
//
//        if (order == null) {
//            return "Order not found";
//        }
//
//        // Update the status to "approved"
//        order.setStatus("approved");
//        orderRepository.save(order);
//        return "Order status updated to approved";
//    }

    // Method to approve an order
    public String approveOrder(int orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order != null) {
            if (!"approved".equals(order.getStatus())) { // Check if it's not already approved
                order.setStatus("approved"); // Set status to "approved"
                orderRepository.save(order); // Save the updated order
                return "Order approved successfully";
            } else {
                return "Order is already approved";
            }
        } else {
            return "Order not found";
        }
    }







}

