package com.example.bookkeeping.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private Long id;
    private BigDecimal amount;
    private String type; // INCOME, EXPENSE
    private Long categoryId;
    private Long userId;
    private String description;
    private LocalDate transactionDate;
    private LocalDateTime createdAt;
}
