package com.example.decoration_backend_springboot.API;
import com.example.decoration_backend_springboot.Model.Order;
import com.example.decoration_backend_springboot.Model.Payment;
import com.example.decoration_backend_springboot.Model.PaymentRequest;
import com.example.decoration_backend_springboot.Model.PaymentResponse;
import com.example.decoration_backend_springboot.Repository.OrderRepository;
import com.example.decoration_backend_springboot.Repository.PaymentRepository;
import com.example.decoration_backend_springboot.Service.OrderService;
import com.example.decoration_backend_springboot.Service.PaymentService;
import com.example.decoration_backend_springboot.Service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/payments")
public class PaymentAPI {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    private Service service;

    @PostMapping("add/payments")
    public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
        try {
            Payment payment1 = paymentService.save(payment);
            return  new ResponseEntity<>("payment was successful posted",HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>("payment was not posted",HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("get/payments")

    public  ResponseEntity<?> getPayment(){
        try {
            List<Payment> PaymentList = paymentService.findAll();
            if (PaymentList.isEmpty()){
                return new ResponseEntity<>("the payment not added", HttpStatus.BAD_REQUEST);
            }
            else {
                return new ResponseEntity<>(PaymentList,HttpStatus.ACCEPTED);
            }
        }catch (Exception e){
            return  new ResponseEntity<>("the payment added successful",HttpStatus.OK);
        }
    }




    @PutMapping("/update/{payment_id}")
    public  ResponseEntity<?> updatePayment(@PathVariable int payment_id ,@RequestBody Payment payment){
        try {
            if (paymentService.findById(payment_id).isPresent()){

                Payment payment1 = paymentService.save(payment);

                return  new ResponseEntity<>("payment updated",HttpStatus.OK);


            }else{
                return new ResponseEntity<>("the payment not updated",HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return  new ResponseEntity<>("payment updated required",HttpStatus.BAD_GATEWAY);
        }
    }
    @DeleteMapping("/delete/{payment_id}")
    public  ResponseEntity<?> deletePayment(@PathVariable int payment_id){

        try {
            paymentService.deleteById(payment_id);
            return new ResponseEntity<>("payment was deleted successful",HttpStatus.OK);

        }catch (Exception e){
            return  new ResponseEntity<>("payment not deleted",HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("getByID/{payment_id}")
    public ResponseEntity<?> getPaymentById(@PathVariable int payment_id){

        try {
            Optional<Payment> optionalPayment = paymentService.findById(payment_id);

            if (optionalPayment.isPresent()){
                return  new ResponseEntity<>(optionalPayment,HttpStatus.OK);

            }
            else {
                return  new ResponseEntity<>("the payment was accessed successful",HttpStatus.OK);
            }

        }catch (Exception e){
            return  new ResponseEntity<>("the payment was not accessed",HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/control-number/{orderId}")
    public ResponseEntity<String> generateControlNumber(@PathVariable int orderId) {
        Order order = orderService.findById(orderId).orElseThrow();
//        Payment payment = paymentService.findById(paymentId).orElseThrow();
        Payment payment1 = new Payment();
        payment1.setOrder(order);
        payment1.setPaymentMethod("atm");
        payment1.setAmount(0);
//        payment1.setStatus("complete");
        paymentService.save(payment1);
        return new ResponseEntity<>(payment1.getControlNumber(), HttpStatus.OK);
    }


    @PostMapping("/orders/{orderId}/payment/{controlNumber}")
    public ResponseEntity<PaymentResponse> payAmount(@PathVariable Long orderId,
                                                     @PathVariable String controlNumber,
                                                     @RequestBody PaymentRequest paymentRequest) {
        // Validate the payment request
        if (paymentRequest.getAmount() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Fetch the order by orderId and control number
        Order order = orderRepository.findByOrderIdAndControlNumber(Math.toIntExact(orderId), controlNumber);

        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Optional: Check if there’s already a payment done for this order
        if ("complete".equals(order.getPaymentStatus())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Payment already completed
        }

        // Create and configure the payment entity
        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
        payment.setStatus("Paid");
        payment.setPaymentMethod("BANK");
        payment.setOrderId(order.getOrderId());
        payment.setOrder(order);

        try {
            // Save the payment
            paymentService.save(payment);

            // Fetch the customer's email from the order
            String customerEmail = order.getCustomer().getEmail(); // Assuming Order has a Customer reference

            // Send a confirmation email
            String subject = "Payment Confirmation";
            String text = "Dear " + order.getCustomer().getFirstName() + ",\n\n" +
                    "Thank you for your payment of " + paymentRequest.getAmount() + ". Your order is now complete.\n\n" +
                    "Order ID: " + orderId + "\n" +
                    "Control Number: " + controlNumber + "\n\n" +
                    "Best regards,\nYour Company";
            service.sendEmail(customerEmail, subject, text);

        } catch (Exception e) {
            // Log the exception (not shown here, but you should log it)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Create and return the payment response
        PaymentResponse paymentResponse = new PaymentResponse(payment);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }



    }


