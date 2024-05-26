package com.example.findpark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "problemDetails")
@Getter
@Setter
@ToString
public class ProblemDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = true)
    private String type;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private int status;

    @Column(nullable = true)
    private String detail;

    @Column(nullable = true)
    private String instance;



}
