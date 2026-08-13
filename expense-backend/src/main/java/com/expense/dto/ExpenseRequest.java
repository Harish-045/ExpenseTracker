package com.expense.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ExpenseRequest {

    @NotBlank(message = "Title is required")
    private String title;
    @Positive(message = "Amount must be greater than zero")
    private Double amount;
    @NotBlank(message = "Category is required")
    private String category;
    @Size(max = 200,
            message = "Description cannot exceed 200 characters")
    private String description;
}