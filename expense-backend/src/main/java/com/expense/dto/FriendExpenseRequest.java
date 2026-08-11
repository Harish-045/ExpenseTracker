package com.expense.dto;

import lombok.Data;

@Data
public class FriendExpenseRequest {

    private String description;

    private Double totalAmount;

    private String friendEmail;
}