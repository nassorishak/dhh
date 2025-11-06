package com.example.decoration_backend_springboot.Service;
import com.example.decoration_backend_springboot.Model.*;
import com.example.decoration_backend_springboot.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private EmailService emailService;

    // Method with preferred network - UPDATED TO USE STOCK DATA
    public Order createOrder(int productId, String customerEmail, String customerPhone,
                             String quantity, String orderType, String size, String customerName,
                             String preferredNetwork) {

        try {
            // Validate input parameters
            validateOrderParameters(productId, customerEmail, customerPhone, quantity, customerName);

            // Find product
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

            // ✅ FIXED: Get stock information instead of product stock
            Stock stock = getStockForProduct(productId);

            // Check stock availability using stock data
            int requestedQuantity = Integer.parseInt(quantity);
            validateStockAvailability(stock, requestedQuantity, product.getProductName());

            // Find or create customer
            Customer customer = findOrCreateCustomer(customerEmail, customerPhone, customerName);

            // Calculate total amount
            BigDecimal totalAmount = calculateTotalAmount(product, requestedQuantity);

            // Create order
            Order order = buildOrder(product, customer, quantity, orderType, size,
                    customerEmail, customerPhone, totalAmount, preferredNetwork);

            Order savedOrder = orderRepository.save(order);

            // ✅ FIXED: Update stock instead of product stock
            updateStockAfterOrder(stock, requestedQuantity);

            // Send email with preferred network or all networks
            sendOrderConfirmationEmail(savedOrder, preferredNetwork);

            return savedOrder;

        } catch (Exception e) {
            throw new RuntimeException("Error creating order: " + e.getMessage(), e);
        }
    }

    private void validateOrderParameters(int productId, String customerEmail, String customerPhone,
                                         String quantity, String customerName) {
        if (productId <= 0) {
            throw new RuntimeException("Invalid product ID");
        }

        if (customerEmail == null || !customerEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new RuntimeException("Invalid email address");
        }

        if (customerPhone == null || customerPhone.trim().isEmpty()) {
            throw new RuntimeException("Phone number is required");
        }

        if (quantity == null || !quantity.matches("\\d+") || Integer.parseInt(quantity) <= 0) {
            throw new RuntimeException("Invalid quantity");
        }

        if (customerName == null || customerName.trim().length() < 2) {
            throw new RuntimeException("Customer name must be at least 2 characters long");
        }
    }

    private Stock getStockForProduct(int productId) {
        List<Stock> stocks = stockRepository.findByProductProductId(productId);
        if (stocks.isEmpty()) {
            throw new RuntimeException("Stock information not found for product ID: " + productId);
        }
        return stocks.get(0);
    }

    private void validateStockAvailability(Stock stock, int requestedQuantity, String productName) {
        // ✅ FIXED: Handle null currentStock safely
        Integer availableStock = stock.getCurrentStock() != null ? stock.getCurrentStock() : 0;

        if (availableStock < requestedQuantity) {
            throw new RuntimeException("Insufficient stock for " + productName +
                    ". Available: " + availableStock + ", Requested: " + requestedQuantity);
        }
    }

    private Customer findOrCreateCustomer(String customerEmail, String customerPhone, String customerName) {
        Customer existingCustomer = customerRepository.findByEmail(customerEmail);

        if (existingCustomer != null) {
            // Update existing customer information
            existingCustomer.setPhone(customerPhone);
            existingCustomer.setName(customerName);
            return customerRepository.save(existingCustomer);
        } else {
            // Create new customer
            Customer newCustomer = new Customer();
            newCustomer.setEmail(customerEmail);
            newCustomer.setPhone(customerPhone);
            newCustomer.setName(customerName);
            return customerRepository.save(newCustomer);
        }
    }

    private BigDecimal calculateTotalAmount(Product product, int quantity) {
        // ✅ FIXED: Handle null sellingPrice safely
        Double sellingPrice = product.getSellingPrice() != null ? product.getSellingPrice() : 0.0;
        return BigDecimal.valueOf(sellingPrice).multiply(BigDecimal.valueOf(quantity));
    }

//    private Order buildOrder(Product product, Customer customer, String quantity, String orderType,
//                             String size, String customerEmail, String customerPhone,
//                             BigDecimal totalAmount, String preferredNetwork) {
//        Order order = new Order();
//        order.setProduct(product);
//        order.setCustomer(customer);
//        order.setQuantity(quantity);
//        order.setOrderType(orderType);
//        order.setSize(size);
//        order.setTotalAmount(totalAmount);
//        order.setCustomerEmail(customerEmail);
//        order.setCustomerPhone(customerPhone);
//        order.setStatus("CONFIRMED");
//        order.setPaymentStatus("PENDING");
//        order.setPreferredNetwork(preferredNetwork);
//        order.setOrderDate(LocalDateTime.now());
//
//        return order;
//    }

    private Order buildOrder(Product product, Customer customer, String quantity, String orderType,
                             String size, String customerEmail, String customerPhone,
                             BigDecimal totalAmount, String preferredNetwork) {
        Order order = new Order();
        order.setProduct(product);
        order.setCustomer(customer);
        order.setQuantity(quantity);
        order.setOrderType(orderType);
        order.setSize(size);
        order.setTotalAmount(totalAmount);
        order.setCustomerEmail(customerEmail);
        order.setCustomerPhone(customerPhone);
        order.setStatus("CONFIRMED");
        order.setPaymentStatus("PENDING");
        order.setPreferredNetwork(preferredNetwork);
        order.setOrderDate(LocalDateTime.now());

        // ✅ FIX: Set customer name from the customer object
        if (customer != null && customer.getName() != null) {
            order.setCustomerName(customer.getName());
            System.out.println("✅ Setting customer name to: " + customer.getName());
        } else {
            // Fallback: use a default name
            order.setCustomerName("Customer");
            System.out.println("⚠️ Customer name not available, using default");
        }

        return order;
    }

    // ✅ FIXED: Update stock after order creation
    private void updateStockAfterOrder(Stock stock, Integer quantity) {
        // Handle null values safely
        Integer currentOutStock = stock.getOutStock() != null ? stock.getOutStock() : 0;
        Integer currentInStock = stock.getInStock() != null ? stock.getInStock() : 0;

        stock.setOutStock(currentOutStock + quantity);
        stock.setCurrentStock(Math.max(0, currentInStock - (currentOutStock + quantity)));

        // Update status based on new stock level
        updateStockStatus(stock);

        stockRepository.save(stock);
    }

    private void updateStockStatus(Stock stock) {
        Integer currentStock = stock.getCurrentStock() != null ? stock.getCurrentStock() : 0;

        if (currentStock <= 0) {
            stock.setStatus("Sold Out");
        } else if (currentStock < 10) {
            stock.setStatus("Low Stock");
        } else {
            stock.setStatus("In Stock");
        }
    }

    private void sendOrderConfirmationEmail(Order order, String preferredNetwork) {
        try {
            if (preferredNetwork != null && !preferredNetwork.trim().isEmpty()) {
                emailService.sendControlNumberEmail(order, preferredNetwork);
            } else {
                emailService.sendControlNumberEmailWithAllNetworks(order);
            }
        } catch (Exception e) {
            // Log email failure but don't fail the order creation
            System.err.println("Failed to send order confirmation email: " + e.getMessage());
        }
    }

    // Overloaded method without network (backward compatibility)
    public Order createOrder(int productId, String customerEmail, String customerPhone,
                             String quantity, String orderType, String size, String customerName) {
        return createOrder(productId, customerEmail, customerPhone, quantity, orderType, size, customerName, null);
    }

    // Return Order instead of Optional<Order>
    public Order getOrderByControlNumber(String controlNumber) {
        Optional<Order> orderOptional = orderRepository.findByControlNumber(controlNumber);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Order not found with control number: " + controlNumber);
        }
        return orderOptional.get();
    }

    public List<Order> getOrdersByCustomerEmail(String email) {
        return orderRepository.findByCustomerEmail(email);
    }

    // Handle Optional properly
    public Order updatePaymentStatus(String controlNumber, String paymentStatus) {
        Order order = getOrderByControlNumber(controlNumber);
        order.setPaymentStatus(paymentStatus);

        if ("PAID".equals(paymentStatus)) {
            order.setStatus("COMPLETED");
            // Send payment confirmation email
            sendPaymentConfirmationEmail(order);
        } else if ("FAILED".equals(paymentStatus)) {
            order.setStatus("PAYMENT_FAILED");
        } else if ("PENDING".equals(paymentStatus)) {
            order.setStatus("AWAITING_PAYMENT");
        }

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> getOrdersByPaymentStatus(String paymentStatus) {
        return orderRepository.findByPaymentStatus(paymentStatus);
    }

    // This method now works with the fixed getOrderByControlNumber
    public Order cancelOrder(String controlNumber) {
        Order order = getOrderByControlNumber(controlNumber);

        // ✅ FIXED: Restore stock instead of product stock
        int returnedQuantity = Integer.parseInt(order.getQuantity());

        // Get stock for the product
        Stock stock = getStockForProduct(order.getProduct().getProductId());

        // Restore stock
        Integer currentOutStock = stock.getOutStock() != null ? stock.getOutStock() : 0;
        Integer currentInStock = stock.getInStock() != null ? stock.getInStock() : 0;

        stock.setOutStock(Math.max(0, currentOutStock - returnedQuantity));
        stock.setCurrentStock(currentInStock - stock.getOutStock());

        // Update status
        updateStockStatus(stock);

        stockRepository.save(stock);

        // Update order status
        order.setStatus("CANCELLED");
        order.setPaymentStatus("REFUNDED");

        return orderRepository.save(order);
    }

    // This method now works with the fixed getOrderByControlNumber
    public Order updateOrder(String controlNumber, String quantity, String orderType, String size) {
        Order order = getOrderByControlNumber(controlNumber);

        // Check if order can be modified
        if (!"CONFIRMED".equals(order.getStatus()) && !"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("Order cannot be modified. Current status: " + order.getStatus());
        }

        // Handle quantity change and stock adjustment
        int oldQuantity = Integer.parseInt(order.getQuantity());
        int newQuantity = Integer.parseInt(quantity);

        if (oldQuantity != newQuantity) {
            Product product = order.getProduct();

            // ✅ FIXED: Use stock data instead of product stock
            Stock stock = getStockForProduct(product.getProductId());

            int stockDifference = newQuantity - oldQuantity;

            // Check stock availability
            Integer availableStock = stock.getCurrentStock() != null ? stock.getCurrentStock() : 0;

            if (stockDifference > availableStock) {
                throw new RuntimeException("Insufficient stock for quantity update. Available: " + availableStock);
            }

            // Update stock
            updateStockAfterOrderChange(stock, oldQuantity, newQuantity);

            // Recalculate total amount
            Double sellingPrice = product.getSellingPrice() != null ? product.getSellingPrice() : 0.0;
            BigDecimal totalAmount = BigDecimal.valueOf(sellingPrice).multiply(BigDecimal.valueOf(newQuantity));
            order.setTotalAmount(totalAmount);
        }

        order.setQuantity(quantity);
        order.setOrderType(orderType);
        order.setSize(size);

        return orderRepository.save(order);
    }

    // ✅ FIXED: Update stock when order quantity changes
    private void updateStockAfterOrderChange(Stock stock, int oldQuantity, int newQuantity) {
        int quantityDifference = newQuantity - oldQuantity;

        Integer currentOutStock = stock.getOutStock() != null ? stock.getOutStock() : 0;
        Integer currentInStock = stock.getInStock() != null ? stock.getInStock() : 0;

        stock.setOutStock(currentOutStock + quantityDifference);
        stock.setCurrentStock(Math.max(0, currentInStock - stock.getOutStock()));

        // Update status
        updateStockStatus(stock);

        stockRepository.save(stock);
    }

    // This method now works with the fixed getOrderByControlNumber
    public void sendPaymentReminder(String controlNumber, String network) {
        Order order = getOrderByControlNumber(controlNumber);

        if ("PAID".equals(order.getPaymentStatus())) {
            throw new RuntimeException("Order is already paid");
        }

        emailService.sendPaymentReminder(order, network);
    }

    // This method now works with the fixed getOrderByControlNumber
    public void sendPaymentReminder(String controlNumber) {
        Order order = getOrderByControlNumber(controlNumber);

        if ("PAID".equals(order.getPaymentStatus())) {
            throw new RuntimeException("Order is already paid");
        }

        emailService.sendPaymentReminder(order, null);
    }

    public List<Order> getPendingPaymentOrders() {
        return orderRepository.findByPaymentStatus("PENDING");
    }

    public List<Order> getCompletedOrders() {
        return orderRepository.findByStatus("COMPLETED");
    }

    public List<Order> getOrdersByProduct(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return orderRepository.findByProduct(product);
    }

    public List<Order> getOrdersByCustomer(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return orderRepository.findByCustomer(customer);
    }

    public BigDecimal getTotalRevenue() {
        List<Order> paidOrders = orderRepository.findByPaymentStatus("PAID");
        return paidOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getTotalOrdersCount() {
        return (int) orderRepository.count();
    }

    public int getPendingOrdersCount() {
        return orderRepository.findByPaymentStatus("PENDING").size();
    }

    public int getCompletedOrdersCount() {
        return orderRepository.findByStatus("COMPLETED").size();
    }

    // Add the missing approveOrder method
    public String approveOrder(int orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if (!"approved".equals(order.getStatus())) {
                order.setStatus("approved");
                orderRepository.save(order);
                return "Order approved successfully";
            } else {
                return "Order is already approved";
            }
        } else {
            return "Order not found";
        }
    }

    // Cancel order by ID method
    public String cancelOrderById(int orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus("canceled");
            orderRepository.save(order);
            return "Order status updated to canceled";
        } else {
            return "Order not found";
        }
    }

    private void sendPaymentConfirmationEmail(Order order) {
        try {
            emailService.sendPaymentConfirmationEmail(order);
        } catch (Exception e) {
            System.err.println("Failed to send payment confirmation email: " + e.getMessage());
        }
    }

    // Bulk operations
    public void processBulkOrders(List<Order> orders) {
        for (Order orderData : orders) {
            try {
                createOrder(
                        orderData.getProduct().getProductId(),
                        orderData.getCustomerEmail(),
                        orderData.getCustomerPhone(),
                        orderData.getQuantity(),
                        orderData.getOrderType(),
                        orderData.getSize(),
                        orderData.getCustomer().getName(),
                        null // default network
                );
            } catch (Exception e) {
                System.err.println("Failed to process order: " + e.getMessage());
                // Continue with next order
            }
        }
    }

    // Search functionality
    public List<Order> searchOrders(String searchTerm) {
        // Search by control number, customer email, customer name, or product name
        return orderRepository.findByControlNumberContainingOrCustomerEmailContainingOrCustomerNameContainingOrProductNameContaining(
                searchTerm, searchTerm, searchTerm, searchTerm);
    }

    // Statistics and analytics
    public OrderStatistics getOrderStatistics() {
        OrderStatistics stats = new OrderStatistics();
        stats.setTotalOrders(getTotalOrdersCount());
        stats.setPendingOrders(getPendingOrdersCount());
        stats.setCompletedOrders(getCompletedOrdersCount());
        stats.setTotalRevenue(getTotalRevenue());
        stats.setAverageOrderValue(calculateAverageOrderValue());
        return stats;
    }

    private BigDecimal calculateAverageOrderValue() {
        List<Order> paidOrders = orderRepository.findByPaymentStatus("PAID");
        if (paidOrders.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = paidOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total.divide(new BigDecimal(paidOrders.size()), 2, BigDecimal.ROUND_HALF_UP);
    }

    // DTO for statistics
    public static class OrderStatistics {
        private int totalOrders;
        private int pendingOrders;
        private int completedOrders;
        private BigDecimal totalRevenue;
        private BigDecimal averageOrderValue;

        // Getters and setters
        public int getTotalOrders() { return totalOrders; }
        public void setTotalOrders(int totalOrders) { this.totalOrders = totalOrders; }

        public int getPendingOrders() { return pendingOrders; }
        public void setPendingOrders(int pendingOrders) { this.pendingOrders = pendingOrders; }

        public int getCompletedOrders() { return completedOrders; }
        public void setCompletedOrders(int completedOrders) { this.completedOrders = completedOrders; }

        public BigDecimal getTotalRevenue() { return totalRevenue; }
        public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

        public BigDecimal getAverageOrderValue() { return averageOrderValue; }
        public void setAverageOrderValue(BigDecimal averageOrderValue) { this.averageOrderValue = averageOrderValue; }
    }
}