package com.example.pool.service;

import com.example.pool.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User findByEmail(String email);
    void save(User user);
}
