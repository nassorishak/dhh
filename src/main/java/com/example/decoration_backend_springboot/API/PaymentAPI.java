package com.example.decoration_backend_springboot.API;
import com.example.decoration_backend_springboot.Model.Order;
import com.example.decoration_backend_springboot.Model.Payment;
import com.example.decoration_backend_springboot.Model.PaymentRequest;
import com.example.decoration_backend_springboot.Model.PaymentResponse;
import com.example.decoration_backend_springboot.Repository.OrderRepository;
import com.example.decoration_backend_springboot.Repository.PaymentRepository;
import com.example.decoration_backend_springboot.Service.OrderService;
import com.example.decoration_backend_springboot.Service.PaymentService;
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
//    @GetMapping("/control-number")
//
//    public  ResponseEntity<String> generateControlNumber(){
//        Payment payment = new Payment();
//        payment.setPaymentMethod("atm");
//        payment.setAmount(20000D);
//        payment.setStatus("complete");
//        paymentService.save(payment);
//        return  new ResponseEntity<>(payment.getControlNumber(),HttpStatus.OK);
//
//    }
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

//    @Autowired
//    private PaymentService paymentService;


//    @PostMapping("/payment/{controlNumber}")
//    public ResponseEntity<PaymentResponse> payAmount(@PathVariable String controlNumber, @RequestBody PaymentRequest paymentRequest) {
//        // Fetch the order by control number
//        Order order = orderRepository.findByControlNumber(controlNumber);
//
//        if (order == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        // Update the payment details for the order
//        Payment payment = new Payment(); // Assuming you have a Payment entity and it's correctly set up
//        payment.setAmount(paymentRequest.getAmount());
//        payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
//        payment.setStatus("complete");
//        payment.setPaymentMethod("BANK");
//
//
//        // Save the payment (you may need to associate it with the order, if applicable)
//        paymentService.save(payment);
//
//        // Create and return the payment response
//        PaymentResponse paymentResponse = new PaymentResponse(payment);
//        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
//    }hii hapo juu
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
    } catch (Exception e) {
        // Log the exception (not shown here, but you should log it)
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Create and return the payment response
    PaymentResponse paymentResponse = new PaymentResponse(payment);
    return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
}
//---------------------------above

//    @PostMapping("/orders/{orderId}/payment/{controlNumber}")
//    public ResponseEntity<PaymentResponse> payAmount(@PathVariable Long orderId,
//                                                     @PathVariable String controlNumber,
//                                                     @RequestBody PaymentRequest paymentRequest) {
//        // Validate the payment request
//        if (paymentRequest.getAmount() <= 0) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        // Fetch the order by orderId and control number
//        Order order = orderRepository.findByOrderIdAndControlNumber(Math.toIntExact(orderId), controlNumber);
//
//        if (order == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        // Optional: Check if there’s already a payment done for this order
//        if ("complete".equals(order.getPaymentStatus())) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT); // Payment already completed
//        }
//
//        // Create and configure the payment entity
//        Payment payment = new Payment();
//        payment.setAmount(paymentRequest.getAmount());
//        payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
//        payment.setStatus("Paid");
//        payment.setPaymentMethod("BANK");
//        payment.setOrderId(order.getOrderId());
//        payment.setOrder(order);
//
//        try {
//            // Save the payment
//            paymentService.save(payment);
//
//            // Update the order status to "Paid"
//            order.setPaymentStatus("Paid");
//            orderRepository.save(order);
//        } catch (Exception e) {
//            // Log the exception (not shown here, but you should log it)
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        // Create and return the payment response
//        PaymentResponse paymentResponse = new PaymentResponse(payment);
//        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
//    }

//@PostMapping("/payment/{controlNumber}")
//public ResponseEntity<PaymentResponse> payAmount(@PathVariable String controlNumber, @RequestBody PaymentRequest paymentRequest) {
//    Payment payment = paymentRepository.findByControlNumber(controlNumber);
//    if (payment == null) {
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    // Set the payment date and amount
//    payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
//    payment.setAmount(paymentRequest.getAmount());
//    payment.setStatus("complete");
//    paymentService.save(payment);
//    PaymentResponse paymentResponse = new PaymentResponse(payment);
//    return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
//}


    }


