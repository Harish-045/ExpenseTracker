package com.expense.dto;

import lombok.Data;

@Data
public class ExpenseRequest {

    private String title;
    private Double amount;
    private String category;
    private String description;
}