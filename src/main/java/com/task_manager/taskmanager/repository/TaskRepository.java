package com.task_manager.taskmanager.repository;

import com.task_manager.taskmanager.entity.Task;
import com.task_manager.taskmanager.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedToId(Long userId);

    List<Task> findByStatus(TaskStatus status);

    long countByStatus(TaskStatus status);

    long countByDueDateBeforeAndStatusNot(LocalDate date, TaskStatus status);

    long countByAssignedToId(Long userId);

    long countByAssignedToIdAndStatus(Long userId, TaskStatus status);

    long countByAssignedToIdAndDueDateBeforeAndStatusNot(Long userId, LocalDate date, TaskStatus status);
}