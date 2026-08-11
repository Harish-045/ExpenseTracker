package com.expense.service;

import com.expense.dto.BudgetRequest;
import com.expense.dto.BudgetResponse;
import com.expense.dto.BudgetStatusResponse;
import com.expense.entity.Budget;
import com.expense.entity.User;
import com.expense.repository.BudgetRepository;
import com.expense.repository.ExpenseRepository;
import com.expense.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

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
    // CREATE BUDGET
    // ==============================

    public BudgetResponse saveBudget(BudgetRequest request) {

        User user = getCurrentUser();

        Budget budget = new Budget();

        budget.setAmount(request.getAmount());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());
        budget.setUser(user);

        Budget savedBudget = budgetRepository.save(budget);

        return new BudgetResponse(
                savedBudget.getId(),
                savedBudget.getAmount(),
                savedBudget.getMonth(),
                savedBudget.getYear()
        );
    }

    // ==============================
    // GET ALL USER BUDGETS
    // ==============================

    public List<BudgetResponse> getAllBudgets() {

        return budgetRepository.findByUser(getCurrentUser())
                .stream()
                .map(budget -> new BudgetResponse(
                        budget.getId(),
                        budget.getAmount(),
                        budget.getMonth(),
                        budget.getYear()
                ))
                .toList();
    }

    // ==============================
    // UPDATE BUDGET
    // ==============================

    public BudgetResponse updateBudget(
            Long id,
            BudgetRequest request) {

        User user = getCurrentUser();

        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Budget not found"));

        // Security check
        if (!budget.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You cannot update another user's budget");
        }

        budget.setAmount(request.getAmount());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());

        Budget updatedBudget =
                budgetRepository.save(budget);

        return new BudgetResponse(
                updatedBudget.getId(),
                updatedBudget.getAmount(),
                updatedBudget.getMonth(),
                updatedBudget.getYear()
        );
    }

    // ==============================
    // DELETE BUDGET
    // ==============================

    public void deleteBudget(Long id) {

        User user = getCurrentUser();

        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Budget not found"));

        if (!budget.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You cannot delete another user's budget");
        }

        budgetRepository.delete(budget);
    }

    // ==============================
    // GET BUDGET STATUS
    // ==============================

    public BudgetStatusResponse getBudgetStatus() {

        User user = getCurrentUser();

        List<Budget> budgets =
                budgetRepository.findByUser(user);

        // No budget exists
        if (budgets.isEmpty()) {

            return new BudgetStatusResponse(
                    0.0,
                    0.0,
                    0.0,
                    0.0
            );
        }

        // Latest budget
        Budget budget = budgets.get(0);

        Double totalExpense =
                expenseRepository.getTotalExpenses(user);

        // No expenses
        if (totalExpense == null) {

            totalExpense = 0.0;
        }

        Double remaining =
                budget.getAmount() - totalExpense;

        Double percentage =
                budget.getAmount() == 0
                        ? 0.0
                        : (totalExpense / budget.getAmount()) * 100;

        // ==============================
        // EMAIL ALERT
        // ==============================

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        if (authentication != null &&
                authentication.isAuthenticated() &&
                percentage >= 80) {

            String userEmail =
                    authentication.getName();

            emailService.sendBudgetAlert(
                    userEmail,
                    percentage,
                    totalExpense,
                    budget.getAmount()
            );
        }

        return new BudgetStatusResponse(
                budget.getAmount(),
                totalExpense,
                remaining,
                percentage
        );
    }

    // ==============================
    // GET SINGLE BUDGET
    // ==============================

    public BudgetResponse getBudgetById(Long id) {

        User user = getCurrentUser();

        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Budget not found"));

        if (!budget.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You cannot view another user's budget");
        }

        return new BudgetResponse(
                budget.getId(),
                budget.getAmount(),
                budget.getMonth(),
                budget.getYear()
        );
    }
}