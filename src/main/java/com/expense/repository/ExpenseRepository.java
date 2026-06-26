package com.expense.repository;

import com.expense.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    @Query("SELECT COALESCE(SUM(e.amount),0) FROM Expense e")

    Double getTotalExpenses();
    @Query("SELECT e.category, SUM(e.amount) " +
            "FROM Expense e GROUP BY e.category")
    List<Object[]> getCategoryWiseExpense();

    @Query("SELECT MONTH(e.date), SUM(e.amount) " +
            "FROM Expense e GROUP BY MONTH(e.date)")
    List<Object[]> getMonthlyExpense();
    @Query("SELECT SUM(e.amount) FROM Expense e")
    Double getTotalExpense();
    List<Expense> findByCategory(String category);
    List<Expense> findByTitleContainingIgnoreCase(String title);
}
