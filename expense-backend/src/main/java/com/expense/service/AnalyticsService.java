package com.expense.service;

import com.expense.dto.AnalyticsResponse;
import com.expense.entity.Budget;
import com.expense.entity.User;
import com.expense.repository.BudgetRepository;
import com.expense.repository.ExpenseRepository;
import com.expense.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    // ==============================
    // GET LOGGED-IN USER
    // ==============================

    private User getCurrentUser() {

        String email =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    // ==============================
    // TOTAL EXPENSE
    // ==============================

    public Double getTotalExpense() {

        Double total =
                expenseRepository.getTotalExpenses(
                        getCurrentUser());

        return total != null ? total : 0.0;
    }

    // ==============================
    // CATEGORY WISE ANALYTICS
    // ==============================

    public List<AnalyticsResponse> categoryWise() {

        User user = getCurrentUser();

        List<Object[]> result =
                expenseRepository.getCategoryWiseExpense(user);

        List<AnalyticsResponse> response =
                new ArrayList<>();

        for (Object[] row : result) {

            response.add(
                    new AnalyticsResponse(
                            row[0].toString(),
                            ((Number) row[1]).doubleValue()
                    )
            );
        }

        return response;
    }

    // ==============================
    // MONTHLY REPORT
    // ==============================

    public List<AnalyticsResponse> monthlyReport() {

        User user = getCurrentUser();

        List<Object[]> result =
                expenseRepository.getMonthlyExpense(user);

        List<AnalyticsResponse> response =
                new ArrayList<>();

        for (Object[] row : result) {

            response.add(
                    new AnalyticsResponse(
                            "Month " + row[0],
                            ((Number) row[1]).doubleValue()
                    )
            );
        }

        return response;
    }

    // ==============================
    // REMAINING BUDGET
    // ==============================

    public Double remainingBudget() {

        User user = getCurrentUser();

        List<Budget> budgets =
                budgetRepository.findByUser(user);

        if (budgets.isEmpty()) {

            return 0.0;
        }

        Budget budget = budgets.get(0);

        Double expense =
                expenseRepository.getTotalExpenses(user);

        if (expense == null) {

            expense = 0.0;
        }

        return budget.getAmount() - expense;
    }
}