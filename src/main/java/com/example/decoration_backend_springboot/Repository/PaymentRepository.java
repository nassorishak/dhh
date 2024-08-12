package com.example.decoration_backend_springboot.Repository;
import com.example.decoration_backend_springboot.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Payment findByControlNumber(String controlNumber);
}
