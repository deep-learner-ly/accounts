package com.example.bookkeeping.service;

import com.example.bookkeeping.mapper.TransactionMapper;
import com.example.bookkeeping.model.ChartData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Service
public class StatsService {

    @Autowired
    private TransactionMapper transactionMapper;

    public List<ChartData> getStats(Long userId, String type) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate;

        if ("week".equalsIgnoreCase(type)) {
            startDate = endDate.minusWeeks(1);
        } else if ("month".equalsIgnoreCase(type)) {
            startDate = endDate.minusMonths(1);
        } else {
            // Default to last 7 days for 'day' or unknown
            startDate = endDate.minusDays(6);
        }

        List<ChartData> rawData = transactionMapper.getDailyStats(userId, startDate, endDate);
        
        if ("day".equalsIgnoreCase(type) || type == null) {
            return fillMissingDays(rawData, startDate, endDate);
        } else if ("week".equalsIgnoreCase(type)) {
             // For week view, we show daily data for the week
             return fillMissingDays(rawData, startDate, endDate);
        } else if ("month".equalsIgnoreCase(type)) {
            // For month, we aggregate by week
            return aggregateByWeek(rawData, startDate, endDate);
        }
        
        return rawData;
    }

    private List<ChartData> fillMissingDays(List<ChartData> data, LocalDate start, LocalDate end) {
        Map<String, ChartData> dataMap = data.stream()
            .collect(Collectors.toMap(ChartData::getLabel, d -> d));
            
        List<ChartData> result = new ArrayList<>();
        LocalDate current = start;
        
        while (!current.isAfter(end)) {
            String label = current.toString();
            ChartData item = dataMap.get(label);
            
            ChartData newItem = new ChartData();
            newItem.setLabel(current.format(DateTimeFormatter.ofPattern("MM-dd")));
            
            if (item != null) {
                newItem.setIncome(item.getIncome());
                newItem.setExpense(item.getExpense());
            } else {
                newItem.setIncome(BigDecimal.ZERO);
                newItem.setExpense(BigDecimal.ZERO);
            }
            
            result.add(newItem);
            current = current.plusDays(1);
        }
        return result;
    }

    private List<ChartData> aggregateByWeek(List<ChartData> data, LocalDate start, LocalDate end) {
        // Use a simpler aggregation for month view: Group by Week number
        // However, standard week fields can be tricky across years. 
        // For simplicity in this demo, let's just group by 7-day chunks relative to start of month?
        // Or better: Use WeekFields.ISO
        
        Map<String, ChartData> weeklyMap = new LinkedHashMap<>();
        WeekFields weekFields = WeekFields.ISO;
        
        // Fill map with data
        for (ChartData d : data) {
            LocalDate date = LocalDate.parse(d.getLabel());
            int weekNum = date.get(weekFields.weekOfWeekBasedYear());
            String weekLabel = "W" + weekNum;
            
            ChartData weekItem = weeklyMap.getOrDefault(weekLabel, new ChartData());
            weekItem.setLabel(weekLabel);
            if (weekItem.getIncome() == null) weekItem.setIncome(BigDecimal.ZERO);
            if (weekItem.getExpense() == null) weekItem.setExpense(BigDecimal.ZERO);
            
            weekItem.setIncome(weekItem.getIncome().add(d.getIncome()));
            weekItem.setExpense(weekItem.getExpense().add(d.getExpense()));
            
            weeklyMap.put(weekLabel, weekItem);
        }
        
        return new ArrayList<>(weeklyMap.values());
    }
}
