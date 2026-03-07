package com.example.bookkeeping.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Category {
    private Long id;
    private String name;
    private String type; // INCOME, EXPENSE
    private Long userId; // null for system default
    private LocalDateTime createdAt;
}
