package com.example.bookkeeping.service;

import com.example.bookkeeping.mapper.UserMapper;
import com.example.bookkeeping.model.User;
import com.example.bookkeeping.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(String phone, String password) {
        if (userMapper.findByPhone(phone) != null) {
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setPhone(phone);
        user.setPassword(hashPassword(password));
        userMapper.insert(user);
        return jwtUtil.generateToken(user.getId(), user.getPhone());
    }

    public String login(String phone, String password) {
        User user = userMapper.findByPhone(phone);
        if (user == null || !user.getPassword().equals(hashPassword(password))) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtil.generateToken(user.getId(), user.getPhone());
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
