package com.example.bookkeeping.controller;

import com.example.bookkeeping.model.Transaction;
import com.example.bookkeeping.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAll(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) userId = 1L;
        return ResponseEntity.ok(transactionService.getAllTransactions(userId));
    }

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId != null) {
            transaction.setUserId(userId);
        } else if (transaction.getUserId() == null) {
            transaction.setUserId(1L);
        }
        return ResponseEntity.ok(transactionService.createTransaction(transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> update(@PathVariable Long id, @RequestBody Transaction transaction, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) userId = 1L;
        transaction.setId(id);
        transaction.setUserId(userId);
        return ResponseEntity.ok(transactionService.updateTransaction(transaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) userId = 1L;
        transactionService.deleteTransaction(id, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats(@RequestParam(defaultValue = "monthly") String period, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) userId = 1L;
        return ResponseEntity.ok(transactionService.getStats(userId, period));
    }
}
