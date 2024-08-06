package com.example.decoration_backend_springboot.Repository;

import com.example.decoration_backend_springboot.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserRepository extends JpaRepository<User,Integer> {
//    @Query(value = "select * from user WHERE email =?1",nativeQuery = true )
    Optional<User> findByEmail(String email);

}
