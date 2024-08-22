package com.example.decoration_backend_springboot.Service;
import com.example.decoration_backend_springboot.Model.Payment;
import com.example.decoration_backend_springboot.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> findAll() {
        return  paymentRepository.findAll();
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void deleteById(int paymentId) {
        paymentRepository.deleteById(paymentId);
    }

    public Optional<Payment> findById(int paymentId) {
        return  paymentRepository.findById(paymentId);
    }
}

