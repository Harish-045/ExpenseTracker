package com.expense.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BudgetResponse {

    private Long id;
    private Double amount;
    private String month;
    private Integer year;
}