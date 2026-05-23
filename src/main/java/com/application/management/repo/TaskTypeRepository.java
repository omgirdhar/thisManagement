package com.application.management.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.management.model.TaskType;

public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {

    List<TaskType> findByProjectIdAndActiveTrue(Long projectId);
}