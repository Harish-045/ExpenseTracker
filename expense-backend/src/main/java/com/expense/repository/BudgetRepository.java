package com.expense.repository;

import com.expense.entity.Budget;
import com.expense.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetRepository
        extends JpaRepository<Budget, Long> {

    List<Budget> findByUser(User user);

    @Query("""
SELECT b
FROM Budget b
WHERE b.user = :user
ORDER BY b.year DESC,
         CASE b.month
             WHEN 'January' THEN 1
             WHEN 'February' THEN 2
             WHEN 'March' THEN 3
             WHEN 'April' THEN 4
             WHEN 'May' THEN 5
             WHEN 'June' THEN 6
             WHEN 'July' THEN 7
             WHEN 'August' THEN 8
             WHEN 'September' THEN 9
             WHEN 'October' THEN 10
             WHEN 'November' THEN 11
             WHEN 'December' THEN 12
         END DESC
""")
    List<Budget> getLatestBudget(User user);
}