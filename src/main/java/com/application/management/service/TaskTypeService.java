package com.application.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.application.management.model.TaskType;
import com.application.management.repo.TaskTypeRepository;

@Service
public class TaskTypeService {

    private final TaskTypeRepository repository;

    public TaskTypeService(TaskTypeRepository repository) {
        this.repository = repository;
    }

    public List<TaskType> getTaskTypesByProject(Long projectId) {
        return repository.findByProjectIdAndActiveTrue(projectId);
    }

    public TaskType save(TaskType taskType) {
        return repository.save(taskType);
    }

	public TaskType getTaskTypeById(Long id) {
		return repository.findById(id).orElse(null);
	}
}