package com.expense.dto;

import lombok.Data;

@Data
public class BudgetRequest {

    private Double amount;
    private String month;
    private Integer year;
}