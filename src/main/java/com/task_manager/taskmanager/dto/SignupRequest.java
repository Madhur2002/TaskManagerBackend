package com.task_manager.taskmanager.dto;

import com.task_manager.taskmanager.entity.Role;
import lombok.Data;

@Data
public class SignupRequest {

    private String name;
    private String email;
    private String password;
    private Role role;
}
