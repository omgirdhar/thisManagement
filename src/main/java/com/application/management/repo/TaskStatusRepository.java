package com.application.management.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.management.model.TaskStatus;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {

    List<TaskStatus> findByProjectIdOrderBySortOrderAsc(Long projectId);
}