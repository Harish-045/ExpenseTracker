package com.expense.repository;

import com.expense.entity.Expense;
import com.expense.entity.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {

    @Query("""
       SELECT COALESCE(SUM(e.amount),0)
       FROM Expense e
       WHERE e.user = :user
       """)
    Double getTotalExpenses(User user);

    @Query("""
       SELECT e.category, SUM(e.amount)
       FROM Expense e
       WHERE e.user = :user
       GROUP BY e.category
       """)
    List<Object[]> getCategoryWiseExpense(User user);

    @Query("""
       SELECT MONTH(e.date), SUM(e.amount)
       FROM Expense e
       WHERE e.user = :user
       GROUP BY MONTH(e.date)
       ORDER BY MONTH(e.date)
       """)
    List<Object[]> getMonthlyExpense(User user);

    List<Expense> findByUser(User user);

    Page<Expense> findByUser(User user, Pageable pageable);

    List<Expense> findByUserAndCategory(User user, String category);

    List<Expense> findByUserAndTitleContainingIgnoreCase(
            User user,
            String title);

    @Query("""
       SELECT e
       FROM Expense e
       WHERE e.user = :user
       ORDER BY e.date DESC, e.id DESC
       """)
    List<Expense> getRecentExpenses(
            User user,
            Pageable pageable);
}