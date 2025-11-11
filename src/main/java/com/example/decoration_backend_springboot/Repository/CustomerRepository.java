package com.example.decoration_backend_springboot.Repository;

import com.example.decoration_backend_springboot.Model.Customer;
import com.example.decoration_backend_springboot.Model.Enum.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByEmail(String email);
        List<Customer> findByRole(Role role);
        List<Customer> findByNameContaining(String name);

}
