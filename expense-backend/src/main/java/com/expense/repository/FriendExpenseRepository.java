package com.expense.repository;

import com.expense.entity.FriendExpense;
import com.expense.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendExpenseRepository
        extends JpaRepository<FriendExpense, Long> {

    List<FriendExpense> findByPaidBy(User user);

    List<FriendExpense> findBySplitWith(User user);
}