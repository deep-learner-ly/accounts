package com.example.bookkeeping.controller;

import com.example.bookkeeping.model.Reminder;
import com.example.bookkeeping.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @GetMapping
    public ResponseEntity<Reminder> getReminder(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) userId = 1L;
        return ResponseEntity.ok(reminderService.getReminder(userId));
    }

    @PostMapping
    public ResponseEntity<Void> saveReminder(@RequestBody Reminder reminder, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) userId = 1L;
        
        reminder.setUserId(userId);
        reminderService.saveReminder(reminder);
        return ResponseEntity.ok().build();
    }
}
