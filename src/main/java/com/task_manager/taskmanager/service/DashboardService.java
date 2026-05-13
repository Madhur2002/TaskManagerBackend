package com.task_manager.taskmanager.service;

import com.task_manager.taskmanager.entity.Role;
import com.task_manager.taskmanager.entity.TaskStatus;
import com.task_manager.taskmanager.entity.User;
import com.task_manager.taskmanager.repository.TaskRepository;
import com.task_manager.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository; // NEW

    public Map<String, Long> getDashboardStats(String userEmail) {

        User user = userRepository.findByEmail(userEmail).orElseThrow();

        long total, done, todo, inProgress, overdue;

        if (user.getRole() == Role.ADMIN) {
            // Global Stats for Admins
            total = taskRepository.count();
            done = taskRepository.countByStatus(TaskStatus.DONE);
            todo = taskRepository.countByStatus(TaskStatus.TODO);
            inProgress = taskRepository.countByStatus(TaskStatus.IN_PROGRESS);
            overdue = taskRepository.countByDueDateBeforeAndStatusNot(LocalDate.now(), TaskStatus.DONE);
        } else {
            // Personal Stats for Members
            Long userId = user.getId();
            total = taskRepository.countByAssignedToId(userId);
            done = taskRepository.countByAssignedToIdAndStatus(userId, TaskStatus.DONE);
            todo = taskRepository.countByAssignedToIdAndStatus(userId, TaskStatus.TODO);
            inProgress = taskRepository.countByAssignedToIdAndStatus(userId, TaskStatus.IN_PROGRESS);
            overdue = taskRepository.countByAssignedToIdAndDueDateBeforeAndStatusNot(userId, LocalDate.now(), TaskStatus.DONE);
        }

        return Map.of(
                "TOTAL", total,
                "DONE", done,
                "TODO", todo,
                "IN_PROGRESS", inProgress,
                "PENDING", todo + inProgress,
                "OVERDUE", overdue
        );
    }
}