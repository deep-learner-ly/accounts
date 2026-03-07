package com.example.bookkeeping.service;

import com.example.bookkeeping.mapper.ReminderMapper;
import com.example.bookkeeping.model.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReminderService {

    @Autowired
    private ReminderMapper reminderMapper;

    public Reminder getReminder(Long userId) {
        return reminderMapper.findByUserId(userId);
    }

    public void saveReminder(Reminder reminder) {
        Reminder existing = reminderMapper.findByUserId(reminder.getUserId());
        if (existing == null) {
            reminderMapper.insert(reminder);
        } else {
            reminderMapper.update(reminder);
        }
    }
}
