package com.example.bookkeeping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @PostMapping
    public ResponseEntity<?> submitFeedback(@RequestBody Map<String, String> feedback, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String content = feedback.get("content");
        String contact = feedback.get("contact");
        
        // In a real app, save to DB or send email
        System.out.println("Feedback received from user " + userId + ": " + content + " (" + contact + ")");
        
        return ResponseEntity.ok(Map.of("message", "Feedback received"));
    }
}
