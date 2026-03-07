package com.example.bookkeeping.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String phone;
    private String password;
    private LocalDateTime createdAt;
}
