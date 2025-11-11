////package com.example.decoration_backend_springboot.API;
////import com.example.decoration_backend_springboot.Model.Order;
////import com.example.decoration_backend_springboot.Model.Payment;
////import com.example.decoration_backend_springboot.Model.PaymentRequest;
////import com.example.decoration_backend_springboot.Model.PaymentResponse;
////import com.example.decoration_backend_springboot.Repository.OrderRepository;
////import com.example.decoration_backend_springboot.Repository.PaymentRepository;
////import com.example.decoration_backend_springboot.Service.OrderService;
////import com.example.decoration_backend_springboot.Service.PaymentService;
////import com.example.decoration_backend_springboot.Service.Service;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////
////import java.sql.Timestamp;
////import java.util.Date;
////import java.util.List;
////import java.util.Optional;
////@RestController
////@CrossOrigin("http://localhost:3000")
////@RequestMapping("api/payments")
////public class PaymentAPI {
////    @Autowired
////    private PaymentService paymentService;
////
////    @Autowired
////    private OrderService orderService;
////
////    @Autowired
////    private OrderRepository orderRepository;
////    @Autowired
////    PaymentRepository paymentRepository;
////
////    @Autowired
////    private Service service;
////
////    @PostMapping("add/payments")
////    public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
////        try {
////            Payment payment1 = paymentService.save(payment);
////            return  new ResponseEntity<>("payment was successful posted",HttpStatus.OK);
////        }catch (Exception e){
////            return  new ResponseEntity<>("payment was not posted",HttpStatus.BAD_REQUEST);
////        }
////
////    }
////    @GetMapping("get/payments")
////
////    public  ResponseEntity<?> getPayment(){
////        try {
////            List<Payment> PaymentList = paymentService.findAll();
////            if (PaymentList.isEmpty()){
////                return new ResponseEntity<>("the payment not added", HttpStatus.BAD_REQUEST);
////            }
////            else {
////                return new ResponseEntity<>(PaymentList,HttpStatus.ACCEPTED);
////            }
////        }catch (Exception e){
////            return  new ResponseEntity<>("the payment added successful",HttpStatus.OK);
////        }
////    }
////
////
////
////
////    @PutMapping("/update/{payment_id}")
////    public  ResponseEntity<?> updatePayment(@PathVariable int payment_id ,@RequestBody Payment payment){
////        try {
////            if (paymentService.findById(payment_id).isPresent()){
////
////                Payment payment1 = paymentService.save(payment);
////
////                return  new ResponseEntity<>("payment updated",HttpStatus.OK);
////
////
////            }else{
////                return new ResponseEntity<>("the payment not updated",HttpStatus.BAD_REQUEST);
////            }
////        }catch (Exception e){
////            return  new ResponseEntity<>("payment updated required",HttpStatus.BAD_GATEWAY);
////        }
////    }
////    @DeleteMapping("/delete/{payment_id}")
////    public  ResponseEntity<?> deletePayment(@PathVariable int payment_id){
////
////        try {
////            paymentService.deleteById(payment_id);
////            return new ResponseEntity<>("payment was deleted successful",HttpStatus.OK);
////
////        }catch (Exception e){
////            return  new ResponseEntity<>("payment not deleted",HttpStatus.BAD_REQUEST);
////        }
////
////    }
////    @GetMapping("getByID/{payment_id}")
////    public ResponseEntity<?> getPaymentById(@PathVariable int payment_id){
////
////        try {
////            Optional<Payment> optionalPayment = paymentService.findById(payment_id);
////
////            if (optionalPayment.isPresent()){
////                return  new ResponseEntity<>(optionalPayment,HttpStatus.OK);
////
////            }
////            else {
////                return  new ResponseEntity<>("the payment was accessed successful",HttpStatus.OK);
////            }
////
////        }catch (Exception e){
////            return  new ResponseEntity<>("the payment was not accessed",HttpStatus.BAD_REQUEST);
////        }
////
////    }
////    @GetMapping("/control-number/{orderId}")
////    public ResponseEntity<String> generateControlNumber(@PathVariable int orderId) {
////        Order order = orderService.findById(orderId).orElseThrow();
//////        Payment payment = paymentService.findById(paymentId).orElseThrow();
////        Payment payment1 = new Payment();
////        payment1.setOrder(order);
////        payment1.setPaymentMethod("atm");
////        payment1.setAmount(0);
//////        payment1.setStatus("complete");
////        paymentService.save(payment1);
////        return new ResponseEntity<>(payment1.getControlNumber(), HttpStatus.OK);
////    }
////
////
//////    @PostMapping("/orders/{orderId}/payment/{controlNumber}")
//////    public ResponseEntity<PaymentResponse> payAmount(@PathVariable Long orderId,
//////                                                     @PathVariable String controlNumber,
//////                                                     @RequestBody PaymentRequest paymentRequest) {
//////        // Validate the payment request
//////        if (paymentRequest.getAmount() <= 0) {
//////            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//////        }
//////
//////        // Fetch the order by orderId and control number
//////        Order order = orderRepository.findByOrderIdAndControlNumber(Math.toIntExact(orderId), controlNumber);
//////
//////        if (order == null) {
//////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//////        }
//////
//////        // Optional: Check if there’s already a payment done for this order
//////        if ("complete".equals(order.getPaymentStatus())) {
//////            return new ResponseEntity<>(HttpStatus.CONFLICT); // Payment already completed
//////        }
//////
//////        // Create and configure the payment entity
//////        Payment payment = new Payment();
//////        payment.setAmount(paymentRequest.getAmount());
//////        payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
//////        payment.setStatus("Paid");
//////        payment.setPaymentMethod("BANK");
//////        payment.setOrderId(order.getOrderId());
//////        payment.setOrder(order);
//////
//////        try {
//////            // Save the payment
//////            paymentService.save(payment);
//////
//////            // Fetch the customer's email from the order
//////            String customerEmail = order.getCustomer().getEmail(); // Assuming Order has a Customer reference
//////
//////            // Send a confirmation email
//////            String subject = "Payment Confirmation";
//////            String text = "Dear " + order.getCustomer().getFirstName() + ",\n\n" +
//////                    "Thank you for your payment of " + paymentRequest.getAmount() + ". Your order is now complete.\n\n" +
//////                    "Order ID: " + orderId + "\n" +
//////                    "Control Number: " + controlNumber + "\n\n" +
//////                    "Best regards,\nYour Company";
//////            service.sendEmail(customerEmail, subject, text);
//////
//////        } catch (Exception e) {
//////            // Log the exception (not shown here, but you should log it)
//////            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//////        }
//////
//////        // Create and return the payment response
//////        PaymentResponse paymentResponse = new PaymentResponse(payment);
//////        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
//////    }
//////
//////
////
////    @PostMapping("/orders/{orderId}/payment/{controlNumber}")
////    public ResponseEntity<PaymentResponse> payAmount(@PathVariable int orderId, // Changed from Long to int
////                                                     @PathVariable String controlNumber,
////                                                     @RequestBody PaymentRequest paymentRequest) {
////
////        // Validate the payment request
////        if (paymentRequest.getAmount() <= 0) {
////            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
////        }
////
////        // Fetch the order by orderId and control number
////        Order order = orderRepository.findByOrderIdAndControlNumber(orderId, controlNumber); // Removed Math.toIntExact
////
////        if (order == null) {
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
////
////        // Optional: Check if there's already a payment done for this order
////        if ("complete".equals(order.getPaymentStatus())) {
////            return new ResponseEntity<>(HttpStatus.CONFLICT); // Payment already completed
////        }
////
////        // Create and configure the payment entity
////        Payment payment = new Payment();
////        payment.setAmount(paymentRequest.getAmount());
////        payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
////        payment.setStatus("Paid");
////        payment.setPaymentMethod("BANK");
////        payment.setOrderId(order.getOrderId());
////        payment.setOrder(order);
////
////        try {
////            // Save the payment
////            paymentService.save(payment);
////
////            // Update order payment status
////            order.setPaymentStatus("complete");
////            orderRepository.save(order);
////
////            // Fetch the customer's email from the order
////            String customerEmail = order.getCustomer().getEmail();
////
////            // Send a confirmation email
////            String subject = "Payment Confirmation";
////            String text = "Dear " + order.getCustomer().getFirstName() + ",\n\n" +
////                    "Thank you for your payment of $" + paymentRequest.getAmount() + ". Your order is now complete.\n\n" +
////                    "Order ID: " + orderId + "\n" +
////                    "Control Number: " + controlNumber + "\n\n" +
////                    "Best regards,\nYour Company";
////            service.sendEmail(customerEmail, subject, text);
////
////        } catch (Exception e) {
////            e.printStackTrace(); // Add proper logging
////            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////
////        // Create and return the payment response
////        PaymentResponse paymentResponse = new PaymentResponse(payment);
////        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
////    }
////
////    }
////
////
//package com.example.decoration_backend_springboot.API;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PaymentAPI {
//
//    @Value("${payment.business.number.tigo:123456}")
//    private String tigoBusinessNumber;
//
//    @Value("${payment.business.number.mpesa:123456}")
//    private String mpesaBusinessNumber;
//
//    @Value("${payment.business.number.airtel:123456}")
//    private String airtelBusinessNumber;
//
//    @Value("${payment.business.number.halopesa:123456}")
//    private String halopesaBusinessNumber;
//
//    @Value("${payment.business.name.tigo:Decoration Store}")
//    private String tigoBusinessName;
//
//    @Value("${payment.business.name.mpesa:Decoration Store}")
//    private String mpesaBusinessName;
//
//    @Value("${payment.business.name.airtel:Decoration Store}")
//    private String airtelBusinessName;
//
//    @Value("${payment.business.name.halopesa:Decoration Store}")
//    private String halopesaBusinessName;
//
//    @Value("${payment.default.network:tigo}")
//    private String defaultNetwork;
//
//    @Value("${company.name:Decoration Store}")
//    private String companyName;
//
//    @Value("${company.email:support@decorationstore.com}")
//    private String companyEmail;
//
//    @Value("${company.phone:+255 123 456 789}")
//    private String companyPhone;
//
//    public String getBusinessNumber(String network) {
//        if (network == null) {
//            network = defaultNetwork;
//        }
//
//        switch (network.toLowerCase()) {
//            case "tigo":
//            case "tigopesa":
//                return tigoBusinessNumber;
//            case "mpesa":
//            case "vodacom":
//                return mpesaBusinessNumber;
//            case "airtel":
//            case "airtelmoney":
//                return airtelBusinessNumber;
//            case "halopesa":
//            case "halotel":
//                return halopesaBusinessNumber;
//            default:
//                return tigoBusinessNumber;
//        }
//    }
//
//    public String getBusinessName(String network) {
//        if (network == null) {
//            network = defaultNetwork;
//        }
//
//        switch (network.toLowerCase()) {
//            case "tigo":
//            case "tigopesa":
//                return tigoBusinessName;
//            case "mpesa":
//            case "vodacom":
//                return mpesaBusinessName;
//            case "airtel":
//            case "airtelmoney":
//                return airtelBusinessName;
//            case "halopesa":
//            case "halotel":
//                return halopesaBusinessName;
//            default:
//                return tigoBusinessName;
//        }
//    }
//
//    public String getPaymentInstructions(String network) {
//        if (network == null) {
//            network = defaultNetwork;
//        }
//
//        switch (network.toLowerCase()) {
//            case "tigo":
//            case "tigopesa":
//                return "1. Dial *150*01# on your Tigo Pesa\\n2. Select 'Lipa kwa Simu'\\n3. Enter Business Number\\n4. Enter Account Number (Control Number)\\n5. Enter Amount\\n6. Enter your PIN and confirm";
//            case "mpesa":
//            case "vodacom":
//                return "1. Dial *150*00# on your M-Pesa\\n2. Select 'Lipa Bill'\\n3. Enter Business Number\\n4. Enter Account Number (Control Number)\\n5. Enter Amount\\n6. Enter your PIN and confirm";
//            case "airtel":
//            case "airtelmoney":
//                return "1. Dial *150*60# on your Airtel Money\\n2. Select 'Make Payment'\\n3. Select 'Pay Bill'\\n4. Enter Business Number\\n5. Enter Account Number (Control Number)\\n6. Enter Amount\\n7. Enter your PIN and confirm";
//            case "halopesa":
//            case "halotel":
//                return "1. Dial *150*99# on your Halotel Pesa\\n2. Select 'Pay Bill'\\n3. Enter Business Number\\n4. Enter Account Number (Control Number)\\n5. Enter Amount\\n6. Enter your PIN and confirm";
//            default:
//                return getPaymentInstructions(defaultNetwork);
//        }
//    }
//
//    public String getDialCode(String network) {
//        if (network == null) {
//            network = defaultNetwork;
//        }
//
//        switch (network.toLowerCase()) {
//            case "tigo":
//            case "tigopesa":
//                return "*150*01#";
//            case "mpesa":
//            case "vodacom":
//                return "*150*00#";
//            case "airtel":
//            case "airtelmoney":
//                return "*150*60#";
//            case "halopesa":
//            case "halotel":
//                return "*150*99#";
//            default:
//                return "*150*01#";
//        }
//    }
//
//    // Getters
//    public String getCompanyName() { return companyName; }
//    public String getCompanyEmail() { return companyEmail; }
//    public String getCompanyPhone() { return companyPhone; }
//    public String getDefaultNetwork() { return defaultNetwork; }
//}
package com.example.decoration_backend_springboot.API;

import com.example.decoration_backend_springboot.Model.*;
import com.example.decoration_backend_springboot.Repository.OrderRepository;
import com.example.decoration_backend_springboot.Repository.PaymentRepository;
import com.example.decoration_backend_springboot.Service.OrderService;
import com.example.decoration_backend_springboot.Service.PaymentService;
import com.example.decoration_backend_springboot.Service.EmailService;
import com.example.decoration_backend_springboot.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/payments")
public class PaymentAPI {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PaymentService mobilePaymentService;

    @Value("${payment.business.number.tigo:123456}")
    private String tigoBusinessNumber;

    @Value("${payment.business.number.mpesa:123456}")
    private String mpesaBusinessNumber;

    @Value("${payment.business.number.airtel:123456}")
    private String airtelBusinessNumber;

    @Value("${payment.business.number.halopesa:123456}")
    private String halopesaBusinessNumber;

    @Value("${payment.business.name:Decoration Store}")
    private String businessName;

    // ==================== MOBILE PAYMENT ENDPOINTS ====================

    @PostMapping("/check-balance")
    public ResponseEntity<?> checkBalance(@RequestBody BalanceCheckRequest request) {
        try {
            // Validate request
            if (request.getPhoneNumber() == null || request.getPhoneNumber().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false, "Phone number is required", null)
                );
            }

            if (request.getNetwork() == null || request.getNetwork().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false, "Network is required", null)
                );
            }

            // Validate Tanzanian phone number format
            String phoneNumber = request.getPhoneNumber().trim();
            if (!mobilePaymentService.isValidTanzanianPhoneNumber(phoneNumber)) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false,
                                "Please enter a valid Tanzanian mobile number (e.g., 0754123456, 0712345678)",
                                null)
                );
            }

            // Check balance using mobile payment service
            Map<String, Object> balanceData = mobilePaymentService.checkRealBalance(
                    phoneNumber,
                    request.getNetwork()
            );

            return ResponseEntity.ok(new PaymentResponse(
                    (Boolean) balanceData.get("success"),
                    (String) balanceData.get("message"),
                    balanceData
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PaymentResponse(false, "Balance check failed: " + e.getMessage(), null));
        }
    }

    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest request) {
        try {
            // Validate request
            if (request.getPhoneNumber() == null || request.getPhoneNumber().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false, "Phone number is required", null)
                );
            }

            if (request.getPin() == null || request.getPin().length() != 4) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false, "Valid 4-digit PIN is required", null)
                );
            }

            if (request.getAmount() <= 0) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false, "Valid amount is required", null)
                );
            }

            if (request.getControlNumber() == null || request.getControlNumber().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false, "Control number is required", null)
                );
            }

            if (request.getNetwork() == null || request.getNetwork().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false, "Network is required", null)
                );
            }

            // Validate Tanzanian phone number format
            String phoneNumber = request.getPhoneNumber().trim();
            if (!mobilePaymentService.isValidTanzanianPhoneNumber(phoneNumber)) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false,
                                "Please enter a valid Tanzanian mobile number",
                                null)
                );
            }

            // Find the order by control number
            Optional<Order> orderOpt = orderRepository.findByControlNumber(request.getControlNumber());
            if (orderOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false,
                                "Order not found with control number: " + request.getControlNumber(),
                                null)
                );
            }

            Order order = orderOpt.get();

            // Check if order amount matches payment amount
            BigDecimal paymentAmount = BigDecimal.valueOf(request.getAmount());
            BigDecimal difference = order.getTotalAmount().subtract(paymentAmount).abs();

            if (difference.compareTo(BigDecimal.valueOf(0.01)) > 0) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false,
                                String.format("Payment amount (Tsh %,.0f) does not match order amount (Tsh %,.2f)",
                                        request.getAmount(), order.getTotalAmount()),
                                null)
                );
            }

            // Check if payment is already completed
            if ("PAID".equalsIgnoreCase(order.getPaymentStatus())) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false, "Payment already completed for this order", null)
                );
            }

            // Check if order is cancelled
            if ("CANCELLED".equalsIgnoreCase(order.getPaymentStatus())) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false, "This order has been cancelled", null)
                );
            }

            // Process payment using mobile payment service
            Map<String, Object> paymentResult = mobilePaymentService.processRealPayment(
                    phoneNumber,
                    request.getPin(),
                    request.getAmount(),
                    request.getNetwork(),
                    request.getControlNumber()
            );

            if (!(Boolean) paymentResult.get("success")) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false, (String) paymentResult.get("error"), null)
                );
            }

            // Create payment record
            Payment payment = createPaymentRecord(request, order, (String) paymentResult.get("transactionId"));

            // Update order payment status
            order.setPaymentStatus("PAID");
            orderRepository.save(order);

            // Send confirmation email
            sendPaymentConfirmationEmail(order, payment, phoneNumber);

            // Prepare response data
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("transactionId", paymentResult.get("transactionId"));
            responseData.put("paymentId", payment.getPaymentId());
            responseData.put("orderId", order.getOrderId());
            responseData.put("controlNumber", order.getControlNumber());
            responseData.put("amount", request.getAmount());
            responseData.put("phoneNumber", phoneNumber);
            responseData.put("network", request.getNetwork());
            responseData.put("paymentDate", new Timestamp(System.currentTimeMillis()));
            responseData.put("customerName", order.getCustomer().getName() + " " + order.getCustomer().getName());
            responseData.put("balance", paymentResult.get("balance"));

            return ResponseEntity.ok(new PaymentResponse(
                    true,
                    "Payment processed successfully",
                    responseData
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PaymentResponse(false, "Payment processing failed: " + e.getMessage(), null));
        }
    }

    @GetMapping("/verify/{transactionId}")
    public ResponseEntity<?> verifyTransaction(@PathVariable String transactionId,
                                               @RequestParam String network) {
        try {
            // Validate inputs
            if (transactionId == null || transactionId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false, "Transaction ID is required", null)
                );
            }

            if (network == null || network.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false, "Network is required", null)
                );
            }

            // Verify transaction using mobile payment service
            Map<String, Object> verificationResult = mobilePaymentService.verifyRealTransaction(
                    transactionId, network);

            return ResponseEntity.ok(new PaymentResponse(
                    (Boolean) verificationResult.get("success"),
                    (String) verificationResult.getOrDefault("message",
                            verificationResult.getOrDefault("error", "Verification completed")),
                    verificationResult
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PaymentResponse(false, "Transaction verification failed: " + e.getMessage(), null));
        }
    }

    @GetMapping("/payment-instructions/{network}")
    public ResponseEntity<?> getPaymentInstructions(@PathVariable String network) {
        try {
            Map<String, Object> instructions = mobilePaymentService.getPaymentInstructions(network);

            if (!(Boolean) instructions.get("success")) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false, (String) instructions.get("error"), null)
                );
            }

            return ResponseEntity.ok(new PaymentResponse(
                    true,
                    "Payment instructions retrieved successfully",
                    instructions
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PaymentResponse(false, "Failed to get payment instructions: " + e.getMessage(), null));
        }
    }

    @GetMapping("/supported-networks")
    public ResponseEntity<?> getSupportedNetworks() {
        try {
            Map<String, Object> networks = mobilePaymentService.getSupportedNetworks();

            return ResponseEntity.ok(new PaymentResponse(
                    true,
                    "Supported networks retrieved successfully",
                    networks
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PaymentResponse(false, "Failed to get supported networks: " + e.getMessage(), null));
        }
    }

    @PostMapping("/validate-phone")
    public ResponseEntity<?> validatePhoneNumber(@RequestBody Map<String, String> request) {
        try {
            String phoneNumber = request.get("phoneNumber");
            String network = request.get("network");

            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponse(false, "Phone number is required", null)
                );
            }

            boolean isValid = mobilePaymentService.isValidTanzanianPhoneNumber(phoneNumber);
            Map<String, Object> validationResult = new HashMap<>();
            validationResult.put("isValid", isValid);
            validationResult.put("phoneNumber", phoneNumber);
            validationResult.put("network", network);
            validationResult.put("formatted", formatPhoneNumber(phoneNumber));

            if (!isValid) {
                validationResult.put("message", "Please enter a valid Tanzanian mobile number");
            } else {
                validationResult.put("message", "Valid Tanzanian mobile number");
            }

            return ResponseEntity.ok(new PaymentResponse(
                    true,
                    "Phone number validation completed",
                    validationResult
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PaymentResponse(false, "Phone validation failed: " + e.getMessage(), null));
        }
    }

    // ==================== HELPER METHODS ====================

    private Payment createPaymentRecord(PaymentRequest request, Order order, String transactionId) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getNetwork().toUpperCase());
        payment.setPhoneNumber(request.getPhoneNumber());
        payment.setTransactionId(transactionId);
        payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
        payment.setStatus("COMPLETED");
        payment.setControlNumber(request.getControlNumber());

        return paymentService.save(payment);
    }

    private void sendPaymentConfirmationEmail(Order order, Payment payment, String phoneNumber) {
        try {
            String customerEmail = order.getCustomer().getEmail();
            String customerName = order.getCustomer().getName() + " " + order.getCustomer().getName();
            String subject = "Payment Confirmation - Order #" + order.getControlNumber();

            String text = "Dear " + customerName + ",\n\n" +
                    "Thank you for your payment! Your order has been successfully processed.\n\n" +
                    "PAYMENT DETAILS:\n" +
                    "---------------\n" +
                    "Order ID: " + order.getOrderId() + "\n" +
                    "Control Number: " + order.getControlNumber() + "\n" +
                    "Amount Paid: Tsh " + String.format("%,.0f", payment.getAmount()) + "\n" +
                    "Payment Method: " + payment.getPaymentMethod() + "\n" +
                    "Phone Number: " + phoneNumber + "\n" +
                    "Transaction ID: " + payment.getTransactionId() + "\n" +
                    "Payment Date: " + payment.getPaymentDate() + "\n\n" +
                    "ORDER DETAILS:\n" +
                    "-------------\n" +
                    "Product: " + (order.getProduct() != null ? order.getProduct().getProductName() : "N/A") + "\n" +
                    "Quantity: " + order.getQuantity() + "\n" +
                    "Total Amount: Tsh " + String.format("%,.0f", order.getTotalAmount()) + "\n\n" +
                    "Your order is now being processed. You will receive another email once your order is shipped.\n\n" +
                    "Thank you for choosing " + businessName + "!\n\n" +
                    "Best regards,\n" +
                    businessName + " Team\n" +
                    "Email: support@decorationstore.com\n" +
                    "Phone: +255 123 456 789";

            emailService.sendEmail(customerEmail, subject, text);

        } catch (Exception e) {
            // Log email sending failure but don't fail the payment
            System.err.println("Failed to send confirmation email for order " + order.getControlNumber() + ": " + e.getMessage());
        }
    }

    private String formatPhoneNumber(String phoneNumber) {
        // Remove any spaces, dashes, or country code
        String cleaned = phoneNumber.replaceAll("[\\s\\-+]", "");

        // If starts with 255, convert to 0
        if (cleaned.startsWith("255") && cleaned.length() == 12) {
            cleaned = "0" + cleaned.substring(3);
        }

        return cleaned;
    }

    // ==================== EXISTING PAYMENT ENDPOINTS ====================

    @PostMapping("/add/payments")
    public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
        try {
            Payment savedPayment = paymentService.save(payment);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Payment was successfully created");
            response.put("paymentId", savedPayment.getPaymentId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new PaymentResponse(false, "Payment creation failed: " + e.getMessage(), null),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/get/payments")
    public ResponseEntity<?> getAllPayments() {
        try {
            List<Payment> paymentList = paymentService.findAll();
            if (paymentList.isEmpty()) {
                return new ResponseEntity<>(
                        new PaymentResponse(false, "No payments found", null),
                        HttpStatus.NOT_FOUND
                );
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("payments", paymentList);
                response.put("count", paymentList.size());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new PaymentResponse(false, "Error retrieving payments: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PutMapping("/update/{payment_id}")
    public ResponseEntity<?> updatePayment(@PathVariable int payment_id, @RequestBody Payment payment) {
        try {
            if (paymentService.findById(payment_id).isPresent()) {
                payment.setPaymentId(payment_id);
                Payment updatedPayment = paymentService.save(payment);

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Payment updated successfully");
                response.put("paymentId", updatedPayment.getPaymentId());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new PaymentResponse(false, "Payment not found with ID: " + payment_id, null),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new PaymentResponse(false, "Error updating payment: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/delete/{payment_id}")
    public ResponseEntity<?> deletePayment(@PathVariable int payment_id) {
        try {
            if (paymentService.findById(payment_id).isPresent()) {
                paymentService.deleteById(payment_id);
                return new ResponseEntity<>(
                        new PaymentResponse(true, "Payment was deleted successfully", null),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        new PaymentResponse(false, "Payment not found with ID: " + payment_id, null),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new PaymentResponse(false, "Payment deletion failed: " + e.getMessage(), null),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/getByID/{payment_id}")
    public ResponseEntity<?> getPaymentById(@PathVariable int payment_id) {
        try {
            Optional<Payment> optionalPayment = paymentService.findById(payment_id);
            if (optionalPayment.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("payment", optionalPayment.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new PaymentResponse(false, "Payment not found with ID: " + payment_id, null),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new PaymentResponse(false, "Error accessing payment: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/by-control-number/{controlNumber}")
    public ResponseEntity<?> getPaymentsByControlNumber(@PathVariable String controlNumber) {
        try {
            List<Payment> payments = (List<Payment>) paymentRepository.findByControlNumber(controlNumber);
            if (payments.isEmpty()) {
                return new ResponseEntity<>(
                        new PaymentResponse(false, "No payments found for control number: " + controlNumber, null),
                        HttpStatus.NOT_FOUND
                );
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("payments", payments);
                response.put("count", payments.size());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new PaymentResponse(false, "Error retrieving payments: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // Simulation endpoints for testing
    @PostMapping("/simulation/reset")
    public ResponseEntity<?> resetSimulation() {
        try {
            mobilePaymentService.resetSimulationData();
            return ResponseEntity.ok(new PaymentResponse(true, "Simulation data reset successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PaymentResponse(false, "Failed to reset simulation: " + e.getMessage(), null));
        }
    }

    @GetMapping("/simulation/data")
    public ResponseEntity<?> getSimulationData() {
        try {
            Map<String, Object> simulationData = mobilePaymentService.getSimulationData();
            return ResponseEntity.ok(new PaymentResponse(true, "Simulation data retrieved", simulationData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PaymentResponse(false, "Failed to get simulation data: " + e.getMessage(), null));
        }
    }
}