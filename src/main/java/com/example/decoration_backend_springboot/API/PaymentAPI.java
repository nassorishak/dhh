package com.example.decoration_backend_springboot.API;
import com.example.decoration_backend_springboot.Model.Payment;
import com.example.decoration_backend_springboot.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin("*")
@RequestMapping("api/payments")
public class PaymentAPI {
    @Autowired
    private PaymentService paymentService;

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
}

