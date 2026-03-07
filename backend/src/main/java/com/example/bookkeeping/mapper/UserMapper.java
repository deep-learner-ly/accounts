package com.example.bookkeeping.mapper;

import com.example.bookkeeping.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE phone = #{phone}")
    User findByPhone(String phone);

    @Insert("INSERT INTO users(phone, password, created_at) VALUES(#{phone}, #{password}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);

    @Update("UPDATE users SET password = #{password} WHERE id = #{id}")
    void updatePassword(Long id, String password);
}
