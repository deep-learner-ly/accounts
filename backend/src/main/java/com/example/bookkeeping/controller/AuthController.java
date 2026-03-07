package com.example.bookkeeping.controller;

import com.example.bookkeeping.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        String password = request.get("password");
        // Aspect will handle logging and exceptions
        String token = authService.register(phone, password);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        String password = request.get("password");
        // Aspect will handle logging and exceptions
        String token = authService.login(phone, password);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/send-code")
    public ResponseEntity<?> sendCode(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        // Aspect will handle logging and exceptions
        String code = authService.sendCode(phone);
        // In production, don't return code. This is for testing.
        return ResponseEntity.ok(Map.of("message", "Code sent", "code", code)); 
    }

    @PostMapping("/login-code")
    public ResponseEntity<?> loginWithCode(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        String code = request.get("code");
        // Aspect will handle logging and exceptions
        String token = authService.loginWithCode(phone, code);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> request, HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        String newPassword = request.get("password");
        authService.updatePassword(userId, newPassword);
        return ResponseEntity.ok().build();
    }
}
