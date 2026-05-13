package com.task_manager.taskmanager.controller;

import com.task_manager.taskmanager.entity.Project;
import com.task_manager.taskmanager.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // 🔥 SECURITY FIX: Only Admins can hit this endpoint
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return ResponseEntity.ok(projectService.createProject(project));
    }

    // Everyone can view all projects
    @GetMapping
    public ResponseEntity<List<Project>> getProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }
}