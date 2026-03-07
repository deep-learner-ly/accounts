package com.example.bookkeeping.controller;

import com.example.bookkeeping.dto.BackupData;
import com.example.bookkeeping.service.BackupService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/backup")
public class BackupController {

    @Autowired
    private BackupService backupService;

    @GetMapping
    public ResponseEntity<BackupData> backup(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(backupService.backup(userId));
    }

    @PostMapping("/restore")
    public ResponseEntity<Void> restore(@RequestBody BackupData data, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        backupService.restore(userId, data);
        return ResponseEntity.ok().build();
    }
}
