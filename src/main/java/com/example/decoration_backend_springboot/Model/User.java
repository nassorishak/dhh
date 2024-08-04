package com.example.decoration_backend_springboot.Model;
import com.example.decoration_backend_springboot.Model.Enum.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity

@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String email;
    private String firstName;
    private String lastName;

    @Column(length = 60)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Getters and setters
}
