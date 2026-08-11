package com.expense.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "friend_expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Double totalAmount;

    private Double splitAmount;

    @ManyToOne
    @JoinColumn(name = "paid_by")
    private User paidBy;

    @ManyToOne
    @JoinColumn(name = "split_with")
    private User splitWith;
}