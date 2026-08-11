package com.expense.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendBudgetAlert(
            String to,
            Double percentage,
            Double spent,
            Double budget) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(to);

        message.setSubject("Expense Tracker - Budget Alert");

        message.setText(
                "Hello,\n\n" +
                        "You have used " +
                        String.format("%.2f", percentage) +
                        "% of your budget.\n\n" +
                        "Budget: ₹" + budget + "\n" +
                        "Spent: ₹" + spent + "\n\n" +
                        "Please review your expenses.\n\n" +
                        "Expense Tracker Team"
        );

        mailSender.send(message);
    }
    public void sendSplitExpenseMail(
            String to,
            String paidBy,
            String description,
            Double totalAmount,
            Double splitAmount) {

        try {

            SimpleMailMessage message =
                    new SimpleMailMessage();

            message.setTo(to);

            message.setSubject(
                    "💸 ExpenseWise - Shared Expense Notification");

            message.setText("""
                Hello,

                %s has shared an expense with you.

                Description : %s
                Total Amount: ₹ %.2f
                Your Share  : ₹ %.2f

                Please settle the amount with your friend.

                Thanks,
                ExpenseWise
                """
                    .formatted(
                            paidBy,
                            description,
                            totalAmount,
                            splitAmount
                    ));

            mailSender.send(message);

            System.out.println(
                    "Split expense email sent to " + to);

        } catch (Exception e) {

            System.out.println(
                    "Failed to send split email: "
                            + e.getMessage());
        }
    }
}