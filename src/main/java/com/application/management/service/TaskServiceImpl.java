package com.application.management.service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.application.management.repo.TaskRepository;
import com.application.management.utils.Enums.Priority;
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
	
	@Override
	public void updatePriority(Long taskId, String priorityValue) {

	    Task task = taskRepository.findById(taskId)
	            .orElseThrow(() -> new RuntimeException("Task not found"));

	    task.setPriority(priorityValue != null ? Priority.valueOf(priorityValue): null);
	    taskRepository.save(task);
	}

	@Override
	public void updateDueDate(Long taskId, String dueDateValue) {

	    Task task = taskRepository.findById(taskId)
	            .orElseThrow(() -> new RuntimeException("Task not found"));

	    if (dueDateValue == null || dueDateValue.isBlank()) {
	        task.setDueDate(null);
	    } else {
	        task.setDueDate(LocalDate.parse(dueDateValue));
	    }

	    taskRepository.save(task);
	}

	@Override
	public void updateStartDate(Long taskId, String startDateValue) {

	    Task task = taskRepository.findById(taskId)
	            .orElseThrow(() -> new RuntimeException("Task not found"));

	    if (startDateValue == null || startDateValue.isBlank()) {
	        task.setStartDate(null);
	    } else {
	        task.setStartDate(LocalDate.parse(startDateValue));
	    }

	    taskRepository.save(task);
	}

	@Override
	public void updateEstimate(Long taskId, String estimateValue) {

	    Task task = taskRepository.findById(taskId)
	            .orElseThrow(() -> new RuntimeException("Task not found"));

	    if (estimateValue == null || estimateValue.isBlank()) {
	        task.setOriginalEstimateMinutes(null);
	        taskRepository.save(task);
	        return;
	    }

	    int totalMinutes = parseEstimateToMinutes(estimateValue);

	    task.setOriginalEstimateMinutes(totalMinutes);
	    taskRepository.save(task);
	}

	private int parseEstimateToMinutes(String input) {

	    input = input.trim().toLowerCase();

	    int totalMinutes = 0;

	    // Match hours (supports decimals)
	    Pattern hourPattern = Pattern.compile("(\\d+(\\.\\d+)?)h");
	    Matcher hourMatcher = hourPattern.matcher(input);

	    if (hourMatcher.find()) {
	        double hours = Double.parseDouble(hourMatcher.group(1));
	        totalMinutes += (int) Math.round(hours * 60);
	    }

	    // Match minutes
	    Pattern minutePattern = Pattern.compile("(\\d+)m");
	    Matcher minuteMatcher = minutePattern.matcher(input);

	    if (minuteMatcher.find()) {
	        totalMinutes += Integer.parseInt(minuteMatcher.group(1));
	    }

	    // If pure number (no h/m), treat as minutes
	    if (!input.contains("h") && !input.contains("m")) {
	        totalMinutes += Integer.parseInt(input);
	    }

	    if (totalMinutes < 0) {
	        throw new IllegalArgumentException("Estimate cannot be negative");
	    }

	    return totalMinutes;
	}

	@Override
	public void updateStatus(Long taskId, String status) {
		Task task = taskRepository.findById(taskId)
	            .orElseThrow(() -> new RuntimeException("Task not found"));

	    task.setStatus(status);
	    taskRepository.save(task);
		
	}

}
