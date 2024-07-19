package com.example.decoration_backend_springboot.Service;
import com.example.decoration_backend_springboot.Model.User;
import com.example.decoration_backend_springboot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return  userRepository.findAll();
    }

    public Optional<User> findById(int userId) {
        return  userRepository.findById(userId);
    }

    public User save(User user) {
        return  userRepository.save(user);
    }

    public void deleteById(int userId) {
        userRepository.deleteById(userId);


    }


    public Optional<User> findByUsername(String username) {
        return findByUsername(username);
    }
}

