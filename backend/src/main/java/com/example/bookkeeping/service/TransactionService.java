package com.example.bookkeeping.service;

import com.example.bookkeeping.mapper.TransactionMapper;
import com.example.bookkeeping.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    public List<Transaction> getAllTransactions(Long userId) {
        return transactionMapper.findAllByUserId(userId);
    }

    public Transaction createTransaction(Transaction transaction) {
        transactionMapper.insert(transaction);
        return transaction;
    }

    public Transaction updateTransaction(Transaction transaction) {
        transactionMapper.update(transaction);
        return transaction;
    }

    public void deleteTransaction(Long id, Long userId) {
        transactionMapper.delete(id, userId);
    }

    public Map<String, Object> getStats(Long userId, String period) {
        LocalDate now = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate = now;

        if ("daily".equalsIgnoreCase(period)) {
            startDate = now;
        } else if ("monthly".equalsIgnoreCase(period)) {
            startDate = now.withDayOfMonth(1);
        } else {
            // Default to monthly
            startDate = now.withDayOfMonth(1);
        }

        BigDecimal totalIncome = transactionMapper.sumAmountByDateRange(userId, "INCOME", startDate, endDate);
        BigDecimal totalExpense = transactionMapper.sumAmountByDateRange(userId, "EXPENSE", startDate, endDate);

        Map<String, Object> stats = new HashMap<>();
        stats.put("period", period);
        stats.put("totalIncome", totalIncome);
        stats.put("totalExpense", totalExpense);
        stats.put("balance", totalIncome.subtract(totalExpense));
        return stats;
    }
}
