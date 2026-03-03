package com.application.management.dto;

import java.time.LocalDate;

import com.application.management.model.Task;

public class TaskEditDTO {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String taskType;
    private Long parentTaskId;
    private Long assigneeId;
    private String estimateFormatted;
    
    public TaskEditDTO(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.priority = task.getPriority() != null ? task.getPriority().name() : null;
        this.startDate = task.getStartDate();
        this.dueDate = task.getDueDate();
        this.taskType = task.getTaskType() != null ? task.getTaskType().name() : null;
        this.parentTaskId = task.getParentTask() != null ? task.getParentTask().getId() : null;
        this.assigneeId = task.getAssignee() != null ? task.getAssignee().getId() : null;
        if (task.getOriginalEstimateMinutes() != null) {
            int minutes = task.getOriginalEstimateMinutes();
            int hours = minutes / 60;
            int remainingMinutes = minutes % 60;

            if (hours > 0 && remainingMinutes > 0) {
                this.estimateFormatted = hours + "h " + remainingMinutes + "m";
            } else if (hours > 0) {
                this.estimateFormatted = hours + "h";
            } else {
                this.estimateFormatted = remainingMinutes + "m";
            }
        } 
    }

    // Empty constructor (required for Jackson if needed)
    public TaskEditDTO() {}
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public Long getParentTaskId() {
		return parentTaskId;
	}
	public void setParentTaskId(Long parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	public Long getAssigneeId() {
		return assigneeId;
	}
	public void setAssigneeId(Long assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getEstimateFormatted() {
		return estimateFormatted;
	}

	public void setEstimateFormatted(String estimateFormatted) {
		this.estimateFormatted = estimateFormatted;
	}
    
}