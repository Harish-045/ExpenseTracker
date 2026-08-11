package com.expense.dto;

import com.expense.entity.Expense;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private Double totalExpense;

    private Double budgetAmount;

    private Double remainingBudget;

    private List<Expense> recentExpenses;

    private Map<String, Double> categoryChart;

    private Map<String, Double> monthlyChart;

}