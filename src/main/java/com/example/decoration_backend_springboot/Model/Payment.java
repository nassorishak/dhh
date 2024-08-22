package com.example.decoration_backend_springboot.Model;//package com.example.decoration_backend_springboot.Model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import java.util.Random;
//
//@Entity
//@Data
//public class Payment {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private int paymentId;
//        private double amount;
//        private String paymentMethod;
//        private String status;
//        private Date PaymentDate;
//        private  String controlNumber;
//
//        @PrePersist
//        public  void generateControlNumber(){
//                if (controlNumber==null){
//                        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//                        String randomPart = String.format("%03d",new Random().nextInt(1000));
//                        controlNumber = "IS-HAK-"+datePart+randomPart;
//
//                }
//        }
//
//        @OneToOne
//        @JoinColumn(name="orderId")
//        private Order order;
//
//
//
//        // getters and setters

import com.example.decoration_backend_springboot.Model.Order;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

@Entity
@Data
public class Payment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int paymentId;
        private double amount;
        private String paymentMethod;
        private String status;
        private Date paymentDate;
        private String controlNumber;

        @PrePersist
        public void generateControlNumber() {
                if (controlNumber == null) {
                        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                        String randomPart = String.format("%03d", new Random().nextInt(1000));
                        controlNumber = "IS-HAK-" + datePart + randomPart;
                }
        }

        @OneToOne
        @JoinColumn(name = "orderId")
        private Order order;

        // getters and setters
}
//}