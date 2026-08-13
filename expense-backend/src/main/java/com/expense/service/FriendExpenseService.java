package com.expense.service;

import com.expense.dto.FriendExpenseRequest;
import com.expense.entity.FriendExpense;
import com.expense.entity.User;
import com.expense.repository.FriendExpenseRepository;
import com.expense.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendExpenseService {

    private final FriendExpenseRepository friendExpenseRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    // ==============================
    // GET LOGGED-IN USER
    // ==============================

    private User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    // ==============================
    // SPLIT EXPENSE
    // ==============================

    public FriendExpense splitExpense(
            FriendExpenseRequest request) {

        User currentUser = getCurrentUser();

        User friend = userRepository
                .findByEmail(request.getFriendEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Friend with email "
                                        + request.getFriendEmail()
                                        + " not found"));

        // Prevent splitting with yourself
        if (friend.getId().equals(currentUser.getId())) {

            throw new RuntimeException(
                    "You cannot split an expense with yourself");
        }

        Double splitAmount =
                request.getTotalAmount() / 2;

        FriendExpense expense = FriendExpense.builder()
                .description(request.getDescription())
                .totalAmount(request.getTotalAmount())
                .splitAmount(splitAmount)
                .paidBy(currentUser)
                .splitWith(friend)
                .build();

        FriendExpense savedExpense =
                friendExpenseRepository.save(expense);

        // ==============================
        // SEND EMAIL TO FRIEND
        // ==============================

        emailService.sendSplitExpenseMail(
                friend.getEmail(),
                currentUser.getName(),
                request.getDescription(),
                request.getTotalAmount(),
                splitAmount
        );

        return savedExpense;
    }

    // ==============================
    // MY SPLITS
    // ==============================

    public List<FriendExpense> mySplits() {

        return friendExpenseRepository
                .findByPaidBy(getCurrentUser());
    }
}