package com.application.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.application.management.model.TaskStatus;
import com.application.management.repo.TaskStatusRepository;

@Service
public class TaskStatusService {

    private final TaskStatusRepository repo;

    public TaskStatusService(TaskStatusRepository repo) {
        this.repo = repo;
    }

    public List<TaskStatus> getByProject(Long projectId) {
        return repo.findByProjectIdOrderBySortOrderAsc(projectId);
    }

	public TaskStatus getById(Long id) {
		return repo.findById(id).orElseThrow(() -> new RuntimeException("Status not found"));
	}

    public TaskStatus save(TaskStatus status) {
        return repo.save(status);
    }
    
    public void delete(Long statusId) {
        repo.delete(getById(statusId));
    } 
}