package com.example.bookkeeping.controller;

import com.example.bookkeeping.model.Transaction;
import com.example.bookkeeping.service.TransactionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/export")
public class ExportController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/csv")
    public void exportCsv(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) userId = 1L; // Fallback for dev

        List<Transaction> transactions = transactionService.getAllTransactions(userId);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"transactions.csv\"");

        try (PrintWriter writer = response.getWriter()) {
            writer.println("ID,Date,Type,Category,Amount,Description");
            for (Transaction t : transactions) {
                writer.printf("%d,%s,%s,%d,%.2f,%s%n",
                        t.getId(),
                        t.getTransactionDate(),
                        t.getType(),
                        t.getCategoryId(),
                        t.getAmount(),
                        t.getDescription() == null ? "" : t.getDescription().replace(",", " "));
            }
        }
    }
}
