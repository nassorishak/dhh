package com.example.decoration_backend_springboot.Service;
import com.example.decoration_backend_springboot.Model.Customer;
import com.example.decoration_backend_springboot.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private  CustomerRepository customerRepository;
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer save(Customer customer) {

        return  customerRepository.save(customer);
    }


    public Optional<Customer> findById(int customerId) {
        return  customerRepository.findById(customerId);
    }

    public void deleteById(int customer_id) {
        customerRepository.deleteById(customer_id);
    }
}

