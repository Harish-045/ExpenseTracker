package com.expense.repository;

import com.expense.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository
        extends JpaRepository<Budget, Long> {
}