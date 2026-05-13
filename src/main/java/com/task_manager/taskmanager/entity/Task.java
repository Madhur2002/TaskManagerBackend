package com.task_manager.taskmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    private User assignedTo;

    @ManyToOne
    private Project project;
}