package com.expense.service;

import com.expense.dto.ExpenseRequest;
import com.expense.entity.Expense;
import com.expense.entity.User;
import com.expense.exception.ExpenseNotFoundException;
import com.expense.repository.ExpenseRepository;
import com.expense.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

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
    // ADD EXPENSE
    // ==============================

    public Expense addExpense(ExpenseRequest request) {

        User user = getCurrentUser();

        Expense expense = Expense.builder()
                .title(request.getTitle())
                .amount(request.getAmount())
                .category(request.getCategory())
                .description(request.getDescription())
                .user(user)
                .build();

        return expenseRepository.save(expense);
    }

    // ==============================
    // GET ALL EXPENSES OF USER
    // ==============================

    public List<Expense> getAllExpenses() {

        return expenseRepository.findByUser(getCurrentUser());
    }

    // ==============================
    // UPDATE EXPENSE
    // ==============================

    public Expense updateExpense(Long id,
                                 ExpenseRequest request) {

        User user = getCurrentUser();

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found with id: " + id));

        // Security check
        if (!expense.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You cannot update another user's expense");
        }

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());

        return expenseRepository.save(expense);
    }

    // ==============================
    // DELETE EXPENSE
    // ==============================

    public void deleteExpense(Long id) {

        User user = getCurrentUser();

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found with id: " + id));

        if (!expense.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You cannot delete another user's expense");
        }

        expenseRepository.delete(expense);
    }

    // ==============================
    // PAGINATION
    // ==============================

    public Page<Expense> getExpenses(int page, int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return expenseRepository.findByUser(
                getCurrentUser(),
                pageable
        );
    }

    // ==============================
    // SORT EXPENSES
    // ==============================

    public List<Expense> getExpensesSorted(String field) {

        List<Expense> expenses =
                expenseRepository.findByUser(getCurrentUser());

        return expenses.stream()
                .sorted(getComparator(field))
                .toList();
    }

    private Comparator<Expense> getComparator(String field) {

        return switch (field) {

            case "title" ->
                    Comparator.comparing(
                            Expense::getTitle,
                            String.CASE_INSENSITIVE_ORDER
                    );

            case "amount" ->
                    Comparator.comparingDouble(
                            Expense::getAmount
                    );

            case "category" ->
                    Comparator.comparing(
                            Expense::getCategory,
                            String.CASE_INSENSITIVE_ORDER
                    );

            case "date" ->
                    Comparator.comparing(
                            Expense::getDate
                    ).reversed();

            default ->
                    Comparator.comparing(
                            Expense::getDate
                    ).reversed();
        };
    }

    // ==============================
    // FILTER BY CATEGORY
    // ==============================

    public List<Expense> getByCategory(String category) {

        return expenseRepository.findByUserAndCategory(
                getCurrentUser(),
                category
        );
    }

    // ==============================
    // SEARCH EXPENSE
    // ==============================

    public List<Expense> searchExpense(String title) {

        return expenseRepository
                .findByUserAndTitleContainingIgnoreCase(
                        getCurrentUser(),
                        title
                );
    }

    // ==============================
    // GET SINGLE EXPENSE
    // ==============================

    public Expense getExpenseById(Long id) {

        User user = getCurrentUser();

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found with id : " + id
                        ));

        if (!expense.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You cannot view another user's expense");
        }

        return expense;
    }

    // ==============================
    // EXPORT TO EXCEL
    // ==============================

    public byte[] exportExpensesToExcel() {

        try {

            List<Expense> expenses =
                    expenseRepository.findByUser(getCurrentUser());

            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("Expenses");

            // Header
            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("Title");
            header.createCell(1).setCellValue("Amount");
            header.createCell(2).setCellValue("Category");
            header.createCell(3).setCellValue("Description");
            header.createCell(4).setCellValue("Date");

            // Data
            int rowNum = 1;

            for (Expense expense : expenses) {

                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(expense.getTitle());
                row.createCell(1).setCellValue(expense.getAmount());
                row.createCell(2).setCellValue(expense.getCategory());
                row.createCell(3).setCellValue(expense.getDescription());

                row.createCell(4).setCellValue(
                        expense.getDate() != null
                                ? expense.getDate().toString()
                                : ""
                );
            }

            // Auto-size columns
            for (int i = 0; i < 5; i++) {

                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out =
                    new ByteArrayOutputStream();

            workbook.write(out);

            workbook.close();

            return out.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to export Excel",
                    e
            );
        }
    }

    // ==============================
    // UPLOAD RECEIPT
    // ==============================

    public String uploadReceipt(Long id,
                                MultipartFile file) {

        try {

            User user = getCurrentUser();

            Expense expense = expenseRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Expense not found"));

            if (!expense.getUser().getId().equals(user.getId())) {

                throw new RuntimeException(
                        "You cannot upload receipt for another user's expense");
            }

            String uploadDir = "uploads/receipts/";

            File folder = new File(uploadDir);

            if (!folder.exists()) {

                folder.mkdirs();
            }

            String fileName =
                    System.currentTimeMillis() + "_" +
                            file.getOriginalFilename();

            Path path = Paths.get(uploadDir, fileName);

            Files.copy(
                    file.getInputStream(),
                    path,
                    StandardCopyOption.REPLACE_EXISTING
            );

            expense.setReceipt(fileName);

            expenseRepository.save(expense);

            return "Receipt Uploaded Successfully";

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to upload receipt",
                    e
            );
        }
    }
}