package com.example.bookkeeping.controller;

import com.example.bookkeeping.model.Transaction;
import com.example.bookkeeping.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void getAllTransactions_Success() throws Exception {
        when(transactionService.getAllTransactions(any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/transactions")
                        .requestAttr("userId", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void createTransaction_Success() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setType("EXPENSE");
        transaction.setTransactionDate(LocalDate.now());

        when(transactionService.createTransaction(any())).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions")
                        .requestAttr("userId", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(100.00));
    }
}
