package com.task_manager.taskmanager.controller;

import com.task_manager.taskmanager.entity.Task;
import com.task_manager.taskmanager.entity.TaskStatus;
import com.task_manager.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // Only ADMIN can assign and create tasks
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    // Both Admin and Member can view tasks
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(Principal principal) {
        return ResponseEntity.ok(taskService.getAllTasks(principal.getName()));
    }

    // 🔥 NEW: Only MEMBERs can update the status of a task
    @PreAuthorize("hasRole('MEMBER')")
    @PutMapping("/{id}/status")
    public ResponseEntity<Task> updateStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        return ResponseEntity.ok(taskService.updateStatus(id, status));
    }
}