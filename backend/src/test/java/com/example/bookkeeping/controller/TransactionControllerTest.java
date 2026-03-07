package com.example.bookkeeping.controller;

import com.example.bookkeeping.model.Transaction;
import com.example.bookkeeping.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private com.example.bookkeeping.config.JwtInterceptor jwtInterceptor;

    @Autowired
    private ObjectMapper objectMapper;

    private Transaction transaction;

    @BeforeEach
    void setUp() throws Exception {
        given(jwtInterceptor.preHandle(any(), any(), any())).willReturn(true);

        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setUserId(1L);
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setType("EXPENSE");
        transaction.setDescription("Lunch");
        transaction.setTransactionDate(LocalDate.now());
    }

    @Test
    public void testGetAllTransactions() throws Exception {
        List<Transaction> transactions = Arrays.asList(transaction);
        given(transactionService.getAllTransactions(1L)).willReturn(transactions);

        mockMvc.perform(get("/api/transactions")
                .requestAttr("userId", 1L)) // Simulate userId from JwtInterceptor
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Lunch"))
                .andExpect(jsonPath("$[0].amount").value(100.00));
    }

    @Test
    public void testCreateTransaction() throws Exception {
        given(transactionService.createTransaction(any(Transaction.class))).willReturn(transaction);

        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction))
                .requestAttr("userId", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Lunch"));
    }
}
