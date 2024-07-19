package com.example.decoration_backend_springboot.Repository;

import com.example.decoration_backend_springboot.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends JpaRepository<User,Integer> {
}
