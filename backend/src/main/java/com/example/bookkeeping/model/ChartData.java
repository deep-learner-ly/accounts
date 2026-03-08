package com.example.bookkeeping.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ChartData {
    private String label;
    private BigDecimal income;
    private BigDecimal expense;
}
