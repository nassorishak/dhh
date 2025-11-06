
package com.example.decoration_backend_springboot.API;

import com.example.decoration_backend_springboot.Model.Order;
import com.example.decoration_backend_springboot.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderAPI {

    @Autowired
    private OrderService orderService;

    // Create order with JSON request body
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            Order order = orderService.createOrder(
                    request.getProductId(),
                    request.getCustomerEmail(),
                    request.getCustomerPhone(),
                    request.getQuantity(),
                    request.getOrderType(),
                    request.getSize(),
                    request.getCustomerName(),
                    request.getPreferredNetwork()
            );
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating order: " + e.getMessage());
        }
    }

    // Backward compatibility - create order without network
    @PostMapping("/create-simple")
    public ResponseEntity<?> createOrderSimple(@RequestBody CreateOrderRequest request) {
        try {
            Order order = orderService.createOrder(
                    request.getProductId(),
                    request.getCustomerEmail(),
                    request.getCustomerPhone(),
                    request.getQuantity(),
                    request.getOrderType(),
                    request.getSize(),
                    request.getCustomerName()
            );
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating order: " + e.getMessage());
        }
    }

    @GetMapping("/control-number/{controlNumber}")
    public ResponseEntity<?> getOrderByControlNumber(@PathVariable String controlNumber) {
        try {
            Order order = orderService.getOrderByControlNumber(controlNumber);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/customer/{email}")
    public ResponseEntity<List<Order>> getOrdersByCustomerEmail(@PathVariable String email) {
        try {
            List<Order> orders = orderService.getOrdersByCustomerEmail(email);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/payment/{controlNumber}")
    public ResponseEntity<?> updatePaymentStatus(
            @PathVariable String controlNumber,
            @RequestParam String paymentStatus) {

        try {
            Order order = orderService.updatePaymentStatus(controlNumber, paymentStatus);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating payment status: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
        try {
            List<Order> orders = orderService.getOrdersByStatus(status);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/payment-status/{paymentStatus}")
    public ResponseEntity<List<Order>> getOrdersByPaymentStatus(@PathVariable String paymentStatus) {
        try {
            List<Order> orders = orderService.getOrdersByPaymentStatus(paymentStatus);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/cancel/{controlNumber}")
    public ResponseEntity<?> cancelOrder(@PathVariable String controlNumber) {
        try {
            Order order = orderService.cancelOrder(controlNumber);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error cancelling order: " + e.getMessage());
        }
    }

    @PutMapping("/update/{controlNumber}")
    public ResponseEntity<?> updateOrder(
            @PathVariable String controlNumber,
            @RequestParam String quantity,
            @RequestParam String orderType,
            @RequestParam String size) {

        try {
            Order order = orderService.updateOrder(controlNumber, quantity, orderType, size);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating order: " + e.getMessage());
        }
    }

    @PostMapping("/reminder/{controlNumber}")
    public ResponseEntity<?> sendPaymentReminder(
            @PathVariable String controlNumber,
            @RequestParam(required = false) String network) {

        try {
            if (network != null && !network.trim().isEmpty()) {
                orderService.sendPaymentReminder(controlNumber, network);
            } else {
                orderService.sendPaymentReminder(controlNumber);
            }
            return ResponseEntity.ok("Payment reminder sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error sending payment reminder: " + e.getMessage());
        }
    }

    @GetMapping("/pending-payment")
    public ResponseEntity<List<Order>> getPendingPaymentOrders() {
        try {
            List<Order> orders = orderService.getPendingPaymentOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Order>> getCompletedOrders() {
        try {
            List<Order> orders = orderService.getCompletedOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Order>> getOrdersByProduct(@PathVariable int productId) {
        try {
            List<Order> orders = orderService.getOrdersByProduct(productId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/customer-id/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable int customerId) {
        try {
            List<Order> orders = orderService.getOrdersByCustomer(customerId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getOrderStatistics() {
        try {
            OrderService.OrderStatistics stats = orderService.getOrderStatistics();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error getting statistics: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Order>> searchOrders(@RequestParam String q) {
        try {
            List<Order> orders = orderService.searchOrders(q);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/revenue")
    public ResponseEntity<?> getTotalRevenue() {
        try {
            BigDecimal revenue = orderService.getTotalRevenue();
            return ResponseEntity.ok(revenue);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error calculating revenue: " + e.getMessage());
        }
    }

    // Inner class for request body
    public static class CreateOrderRequest {
        private int productId;
        private String customerEmail;
        private String customerPhone;
        private String quantity;
        private String orderType;
        private String size;
        private String customerName;
        private String preferredNetwork;

        // Getters and setters
        public int getProductId() { return productId; }
        public void setProductId(int productId) { this.productId = productId; }

        public String getCustomerEmail() { return customerEmail; }
        public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

        public String getCustomerPhone() { return customerPhone; }
        public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

        public String getQuantity() { return quantity; }
        public void setQuantity(String quantity) { this.quantity = quantity; }

        public String getOrderType() { return orderType; }
        public void setOrderType(String orderType) { this.orderType = orderType; }

        public String getSize() { return size; }
        public void setSize(String size) { this.size = size; }

        public String getCustomerName() { return customerName; }
        public void setCustomerName(String customerName) { this.customerName = customerName; }

        public String getPreferredNetwork() { return preferredNetwork; }
        public void setPreferredNetwork(String preferredNetwork) { this.preferredNetwork = preferredNetwork; }
    }
}