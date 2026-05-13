package com.task_manager.taskmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    private User createdBy;

    @ManyToMany
    private List<User> members;
}