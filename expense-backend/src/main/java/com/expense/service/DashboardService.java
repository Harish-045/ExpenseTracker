package com.expense.service;

import com.expense.dto.DashboardResponse;
import com.expense.entity.Budget;
import com.expense.entity.Expense;
import com.expense.entity.User;
import com.expense.repository.BudgetRepository;
import com.expense.repository.ExpenseRepository;
import com.expense.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

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
    // DASHBOARD DATA
    // ==============================

    public DashboardResponse getDashboard() {

        User user = getCurrentUser();

        // ------------------------------
        // Total Expense
        // ------------------------------

        Double totalExpense =
                expenseRepository.getTotalExpenses(user);

        if (totalExpense == null) {

            totalExpense = 0.0;
        }

        // ------------------------------
        // Latest Budget
        // ------------------------------

        List<Budget> latestBudgets =
                budgetRepository.getLatestBudget(user);

        Budget latestBudget =
                latestBudgets.isEmpty()
                        ? null
                        : latestBudgets.get(0);

        Double budgetAmount =
                latestBudget != null
                        ? latestBudget.getAmount()
                        : 0.0;

        Double remainingBudget =
                budgetAmount - totalExpense;

        // ------------------------------
        // Recent Expenses
        // ------------------------------

        List<Expense> recentExpenses =
                expenseRepository.getRecentExpenses(
                        user,
                        PageRequest.of(0, 5)
                );

        // ------------------------------
        // Category Chart
        // ------------------------------

        Map<String, Double> categoryChart =
                new LinkedHashMap<>();

        List<Object[]> categoryData =
                expenseRepository.getCategoryWiseExpense(user);

        for (Object[] row : categoryData) {

            categoryChart.put(
                    row[0].toString(),
                    ((Number) row[1]).doubleValue()
            );
        }

        // ------------------------------
        // Monthly Chart
        // ------------------------------

        Map<String, Double> monthlyChart =
                new LinkedHashMap<>();

        List<Object[]> monthlyData =
                expenseRepository.getMonthlyExpense(user);

        for (Object[] row : monthlyData) {

            int month =
                    ((Number) row[0]).intValue();

            monthlyChart.put(
                    Month.of(month).name(),
                    ((Number) row[1]).doubleValue()
            );
        }

        // ------------------------------
        // Build Response
        // ------------------------------

        return DashboardResponse.builder()

                .totalExpense(totalExpense)

                .budgetAmount(budgetAmount)

                .remainingBudget(remainingBudget)

                .recentExpenses(recentExpenses)

                .categoryChart(categoryChart)

                .monthlyChart(monthlyChart)

                .build();
    }
}