package com.expense.service;

import com.expense.dto.AnalyticsResponse;
import com.expense.entity.Budget;
import com.expense.repository.BudgetRepository;
import com.expense.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import com.expense.dto.DashboardResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;

    public Double getTotalExpense() {
        return expenseRepository.getTotalExpense();
    }

    public List<AnalyticsResponse> categoryWise() {

        List<Object[]> result =
                expenseRepository.getCategoryWiseExpense();

        List<AnalyticsResponse> response =
                new ArrayList<>();

        for (Object[] row : result) {
            response.add(
                    new AnalyticsResponse(
                            row[0].toString(),
                            (Double) row[1]
                    )
            );
        }

        return response;
    }

    public List<AnalyticsResponse> monthlyReport() {

        List<Object[]> result =
                expenseRepository.getMonthlyExpense();

        List<AnalyticsResponse> response =
                new ArrayList<>();

        for (Object[] row : result) {
            response.add(
                    new AnalyticsResponse(
                            "Month " + row[0],
                            (Double) row[1]
                    )
            );
        }

        return response;
    }

    public Double remainingBudget() {

        Budget budget =
                budgetRepository.findAll().get(0);

        Double expense =
                expenseRepository.getTotalExpense();

        return budget.getAmount() - expense;
    }
    public DashboardResponse getDashboardData() {

        Double total = expenseRepository.getTotalExpense();

        List<Map<String, Object>> categoryData =
                expenseRepository.getCategoryWiseExpense()
                        .stream()
                        .map(obj -> Map.of(
                                "category", obj[0],
                                "amount", obj[1]
                        ))
                        .toList();

        List<Map<String, Object>> monthlyData =
                expenseRepository.getMonthlyExpense()
                        .stream()
                        .map(obj -> Map.of(
                                "month", obj[0],
                                "amount", obj[1]
                        ))
                        .toList();

        return new DashboardResponse(
                total,
                categoryData,
                monthlyData
        );
    }
}