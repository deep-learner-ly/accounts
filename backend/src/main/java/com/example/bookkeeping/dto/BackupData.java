package com.example.bookkeeping.dto;

import com.example.bookkeeping.model.Category;
import com.example.bookkeeping.model.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class BackupData {
    private List<Transaction> transactions;
    private List<Category> categories;
}
