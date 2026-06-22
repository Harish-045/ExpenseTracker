package com.expense.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BudgetStatusResponse {

    private Double budget;
    private Double spent;
    private Double remaining;
    private Double percentageUsed;
}