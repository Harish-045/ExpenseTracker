package com.expense.service;

import com.expense.dto.BudgetRequest;
import com.expense.dto.BudgetResponse;
import com.expense.dto.BudgetStatusResponse;
import com.expense.entity.Budget;
import com.expense.repository.BudgetRepository;
import com.expense.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final ExpenseRepository expenseRepository;
    public BudgetResponse saveBudget(BudgetRequest request) {

        Budget budget = new Budget();
        budget.setAmount(request.getAmount());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());

        Budget savedBudget = budgetRepository.save(budget);

        return new BudgetResponse(
                savedBudget.getId(),
                savedBudget.getAmount(),
                savedBudget.getMonth(),
                savedBudget.getYear()
        );
    }

    public List<BudgetResponse> getAllBudgets() {
        return budgetRepository.findAll()
                .stream()
                .map(budget -> new BudgetResponse(
                        budget.getId(),
                        budget.getAmount(),
                        budget.getMonth(),
                        budget.getYear()
                ))
                .toList();
    }

    public BudgetResponse updateBudget(Long id,   @RequestBody BudgetRequest request) {

        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Budget not found"));

        budget.setAmount(request.getAmount());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());

        Budget updatedBudget = budgetRepository.save(budget);

        return new BudgetResponse(
                updatedBudget.getId(),
                updatedBudget.getAmount(),
                updatedBudget.getMonth(),
                updatedBudget.getYear()
        );
    }

    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }


    public BudgetStatusResponse getBudgetStatus() {

        Budget budget = budgetRepository.findAll().get(0);

        Double totalExpense = expenseRepository.getTotalExpenses();

        Double remaining = budget.getAmount() - totalExpense;

        Double percentage =
                (totalExpense / budget.getAmount()) * 100;

        return new BudgetStatusResponse(
                budget.getAmount(),
                totalExpense,
                remaining,
                percentage
        );
    }
}