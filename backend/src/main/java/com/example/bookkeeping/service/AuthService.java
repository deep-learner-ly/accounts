package com.example.bookkeeping.service;

import com.example.bookkeeping.mapper.UserMapper;
import com.example.bookkeeping.model.User;
import com.example.bookkeeping.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

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

    public String sendCode(String phone) {
        // Generate 6-digit code
        String code = String.format("%06d", new Random().nextInt(999999));
        verificationCodes.put(phone, code);
        // In a real app, send SMS here. For now, just log it.
        System.out.println("Verification code for " + phone + ": " + code);
        return code; // Return code for testing convenience
    }

    public String loginWithCode(String phone, String code) {
        String storedCode = verificationCodes.get(phone);
        if (storedCode == null || !storedCode.equals(code)) {
            throw new RuntimeException("Invalid or expired verification code");
        }
        
        // Clear code after use
        verificationCodes.remove(phone);

        User user = userMapper.findByPhone(phone);
        if (user == null) {
            // Register new user with random password
            user = new User();
            user.setPhone(phone);
            user.setPassword(hashPassword(UUID.randomUUID().toString()));
            try {
                userMapper.insert(user);
            } catch (Exception e) {
                // If insert fails (e.g. race condition), try to fetch again
                user = userMapper.findByPhone(phone);
                if (user == null) {
                    throw new RuntimeException("Failed to register user", e);
                }
            }
        }
        
        return jwtUtil.generateToken(user.getId(), user.getPhone());
    }

    public void updatePassword(Long userId, String newPassword) {
        userMapper.updatePassword(userId, hashPassword(newPassword));
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
