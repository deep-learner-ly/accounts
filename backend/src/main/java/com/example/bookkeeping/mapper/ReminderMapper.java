package com.example.bookkeeping.mapper;

import com.example.bookkeeping.model.Reminder;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ReminderMapper {

    @Select("SELECT * FROM reminders WHERE user_id = #{userId}")
    Reminder findByUserId(Long userId);

    @Insert("INSERT INTO reminders(user_id, enabled, time, repeat_days) VALUES(#{userId}, #{enabled}, #{time}, #{repeatDays})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Reminder reminder);

    @Update("UPDATE reminders SET enabled=#{enabled}, time=#{time}, repeat_days=#{repeatDays} WHERE user_id=#{userId}")
    void update(Reminder reminder);
}
