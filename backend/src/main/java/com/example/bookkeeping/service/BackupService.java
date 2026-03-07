package com.example.bookkeeping.service;

import com.example.bookkeeping.dto.BackupData;
import com.example.bookkeeping.mapper.CategoryMapper;
import com.example.bookkeeping.mapper.TransactionMapper;
import com.example.bookkeeping.model.Category;
import com.example.bookkeeping.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class BackupService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public BackupData backup(Long userId) {
        BackupData data = new BackupData();
        data.setTransactions(transactionMapper.findAllByUserId(userId));
        data.setCategories(categoryMapper.findAllByUserId(userId));
        return data;
    }

    @Transactional
    public void restore(Long userId, BackupData data) {
        Map<Long, Long> categoryIdMap = new HashMap<>();

        if (data.getCategories() != null) {
            for (Category cat : data.getCategories()) {
                Long oldId = cat.getId();
                if (cat.getUserId() != null) {
                    // Custom category: re-insert
                    cat.setId(null); 
                    cat.setUserId(userId); // Ensure it belongs to current user
                    categoryMapper.insert(cat);
                    categoryIdMap.put(oldId, cat.getId());
                } else {
                    // System category: keep ID
                    categoryIdMap.put(oldId, oldId);
                }
            }
        }

        if (data.getTransactions() != null) {
            for (Transaction tx : data.getTransactions()) {
                tx.setId(null); 
                tx.setUserId(userId); 
                
                // Remap category ID if needed
                if (tx.getCategoryId() != null && categoryIdMap.containsKey(tx.getCategoryId())) {
                    tx.setCategoryId(categoryIdMap.get(tx.getCategoryId()));
                }
                
                transactionMapper.insert(tx);
            }
        }
    }
}
