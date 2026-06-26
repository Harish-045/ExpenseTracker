package com.expense.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class DashboardResponse {

    private Double totalExpense;

    private List<Map<String, Object>> categoryWiseExpense;

    private List<Map<String, Object>> monthlyExpense;
}