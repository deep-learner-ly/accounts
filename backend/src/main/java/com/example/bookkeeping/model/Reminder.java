package com.example.bookkeeping.model;

import lombok.Data;
import java.time.LocalTime;

@Data
public class Reminder {
    private Long id;
    private Long userId;
    private Boolean enabled;
    private LocalTime time;
    private String repeatDays; // e.g. "1,2,3,4,5" for Mon-Fri
}
