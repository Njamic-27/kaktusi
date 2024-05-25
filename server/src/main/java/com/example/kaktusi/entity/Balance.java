package com.example.kaktusi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "balance")
@Getter
@Setter
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    Integer balance;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
