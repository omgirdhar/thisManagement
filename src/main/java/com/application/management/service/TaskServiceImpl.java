package com.application.management.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.application.management.repo.TaskRepository;
import com.application.management.utils.Enums.Priority;
import com.application.management.utils.Enums.TaskType;
import com.application.management.utils.TimeFormatUtils;

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
		return taskRepository.findUserTasks(user, project);
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
	public void updateTask(Long taskId, Map<String, Object> updates) {
	    Task task = taskRepository.findById(taskId)
	            .orElseThrow(() -> new RuntimeException("Task not found"));

	    if (updates.containsKey("description")) {
	        task.setDescription((String) updates.get("description"));
	    }
	    if (updates.containsKey("title")) {
	        task.setTitle((String) updates.get("title"));
	    }
	    if (updates.containsKey("priority")) {
	        String value = (String) updates.get("priority");
	        task.setPriority(value != null ? Priority.valueOf(value) : null);
	    }
	    if (updates.containsKey("dueDate")) {
	        String dueDate = (String) updates.get("dueDate");
	        task.setDueDate(dueDate == null || dueDate.isBlank() ? null : LocalDate.parse(dueDate));
	    }
	    if (updates.containsKey("startDate")) {
	        String startDate = (String) updates.get("startDate");
	        task.setStartDate(startDate == null || startDate.isBlank() ? null : LocalDate.parse(startDate));
	    }
	    if (updates.containsKey("estimate")) {
	        String estimate = (String) updates.get("estimate");
	        task.setOriginalEstimateMinutes(
	                estimate == null || estimate.isBlank() ? null : TimeFormatUtils.parseEstimateToMinutes(estimate)
	        );
	    }
	    if (updates.containsKey("status")) {
	        task.setStatus((String) updates.get("status"));
	    }
	    if (updates.containsKey("assigneeId")) {
	        Object assigneeIdObj = updates.get("assigneeId");
	        if (assigneeIdObj == null || assigneeIdObj.toString().isBlank()) {
	            task.setAssignee(null);
	        } else {
	            Long assigneeId = Long.valueOf(assigneeIdObj.toString());
	            User user = userService.getUserById(assigneeId);
	            if (user == null) throw new RuntimeException("User not found");
	            task.setAssignee(user);
	        }
	    }

	    taskRepository.save(task);
	}

}
