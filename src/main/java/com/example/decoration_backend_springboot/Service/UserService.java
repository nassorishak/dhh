package com.example.decoration_backend_springboot.Service;
import com.example.decoration_backend_springboot.Model.Enum.Role;
import com.example.decoration_backend_springboot.Model.User;
import com.example.decoration_backend_springboot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired

    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(int userId) {
        return userRepository.findById(userId);
    }

    public User save(User user) {

        User createUser = new User();
        createUser.setEmail(user.getEmail());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.setFirstName(user.getFirstName());
        createUser.setLastName(user.getLastName());
        createUser.setRole(user.getRole());

        return userRepository.save(createUser);
    }

    public void deleteById(int userId) {
        userRepository.deleteById(userId);


    }


    public Optional<User> findByUsername(String username) {
        return findByUsername(username);


    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User authenticate(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

}





