package com.task_manager.taskmanager.service;

import com.task_manager.taskmanager.entity.Role;
import com.task_manager.taskmanager.entity.Task;
import com.task_manager.taskmanager.entity.TaskStatus;
import com.task_manager.taskmanager.entity.User;
import com.task_manager.taskmanager.repository.TaskRepository;
import com.task_manager.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Task createTask(Task task) {
        // Fetch the selected user from the database and assign them
        if (task.getAssignedTo() != null && task.getAssignedTo().getId() != null) {
            User assignee = userRepository.findById(task.getAssignedTo().getId()).orElseThrow();
            task.setAssignedTo(assignee);
        }

        task.setStatus(TaskStatus.TODO);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        // ADMIN sees everything. MEMBER sees only their assigned tasks.
        if (user.getRole() == Role.ADMIN) {
            return taskRepository.findAll();
        }
        return taskRepository.findByAssignedToId(user.getId());
    }

    public Task updateStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setStatus(status);
        return taskRepository.save(task);
    }
}