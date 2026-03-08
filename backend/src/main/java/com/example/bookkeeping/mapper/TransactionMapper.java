package com.example.bookkeeping.mapper;

import com.example.bookkeeping.model.ChartData;
import com.example.bookkeeping.model.Transaction;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TransactionMapper {

    @Select("SELECT * FROM transactions WHERE user_id = #{userId} ORDER BY transaction_date DESC")
    List<Transaction> findAllByUserId(Long userId);

    @Insert("INSERT INTO transactions(amount, type, category_id, user_id, description, transaction_date, created_at) " +
            "VALUES(#{amount}, #{type}, #{categoryId}, #{userId}, #{description}, #{transactionDate}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Transaction transaction);

    @Update("UPDATE transactions SET amount=#{amount}, type=#{type}, category_id=#{categoryId}, " +
            "description=#{description}, transaction_date=#{transactionDate} WHERE id=#{id} AND user_id=#{userId}")
    void update(Transaction transaction);

    @Delete("DELETE FROM transactions WHERE id=#{id} AND user_id=#{userId}")
    void delete(@Param("id") Long id, @Param("userId") Long userId);

    @Select("SELECT * FROM transactions WHERE id = #{id} AND user_id = #{userId}")
    Transaction findById(@Param("id") Long id, @Param("userId") Long userId);

    @Select("SELECT COALESCE(SUM(amount), 0) FROM transactions " +
            "WHERE user_id = #{userId} AND type = #{type} " +
            "AND transaction_date BETWEEN #{startDate} AND #{endDate}")
    BigDecimal sumAmountByDateRange(@Param("userId") Long userId,
                                    @Param("type") String type,
                                    @Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate);

    @Select("SELECT TO_CHAR(transaction_date, 'YYYY-MM-DD') as label, " +
            "SUM(CASE WHEN type = 'INCOME' THEN amount ELSE 0 END) as income, " +
            "SUM(CASE WHEN type = 'EXPENSE' THEN amount ELSE 0 END) as expense " +
            "FROM transactions " +
            "WHERE user_id = #{userId} AND transaction_date BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY transaction_date " +
            "ORDER BY transaction_date")
    List<ChartData> getDailyStats(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
