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



}

