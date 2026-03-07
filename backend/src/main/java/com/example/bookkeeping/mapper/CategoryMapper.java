package com.example.bookkeeping.mapper;

import com.example.bookkeeping.model.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("SELECT * FROM categories WHERE user_id IS NULL OR user_id = #{userId}")
    List<Category> findAllByUserId(Long userId);

    @Select("SELECT * FROM categories WHERE id = #{id}")
    Category findById(Long id);

    @Insert("INSERT INTO categories(name, type, user_id, created_at) VALUES(#{name}, #{type}, #{userId}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Category category);
}
