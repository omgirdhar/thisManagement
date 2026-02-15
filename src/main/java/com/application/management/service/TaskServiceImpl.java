package com.application.management.service;

import java.util.List;
import com.application.management.repo.TaskRepository;
import com.application.management.utils.Enums.TaskType;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.application.management.model.Project;
import com.application.management.model.Task;
import com.application.management.model.User;

@Service
public class TaskServiceImpl implements TaskService{

    private final UserService userService;

    private final TaskRepository taskRepository;

    TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

	@Override
	public List<Task> getTasksForUser(Project project) {
		User user = userService.getCurrentUser();
		return taskRepository.findByAssigneeAndCreatedByAndProjectAndParentTaskNull(user, user,project);
	}
	
	@Override
	public Task getTaskById(Long id) {
		return taskRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Task not found"));
	}
	
	@Override
    public Task saveTask(Task task) {
		task.setCreatedBy( userService.getCurrentUser());
        return taskRepository.save(task);
    }
	
	@Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
	
	@Override
	public List<Task> getChildTasksByParentTaskId(Task task){
		return taskRepository.findByParentTask(task);
	}

	@Transactional
	public void updateDescription(Long taskId, String description) {
	    Task task = taskRepository.findById(taskId)
	            .orElseThrow(() -> new RuntimeException("Task not found"));

	    task.setDescription(description);
	    taskRepository.save(task);
	}

}
