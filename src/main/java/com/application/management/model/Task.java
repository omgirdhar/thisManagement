package com.application.management.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.application.management.utils.Enums.Priority;
import com.application.management.utils.Enums.TaskType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String status;

    private LocalDate dueDate;
    
    private LocalDate startDate;
    
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;
    
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    
    @Enumerated(EnumType.STRING)
    private TaskType taskType;
    
    @ManyToOne
    @JoinColumn(name = "parent_task_id")
    private Task parentTask;

    @OneToMany(mappedBy = "parentTask")
    private List<Task> subTasks = new ArrayList<>();
    
    private String description;
    
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    
//    New fields to Add
    private Priority  priority; 
    
    private Integer originalEstimateMinutes;

//    created_at
//    updated_at
//    assigned_to
//    due_date
//    start_date
//    completed_at
//    estimated_time
//    actual_time
//    comments

    public Task() {}

    public Task(Long id, String title, String status, LocalDate dueDate, User assignee, Project project) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.dueDate = dueDate;
        this.assignee = assignee;
        this.project = project;
    }

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public Task getParentTask() {
		return parentTask;
	}

	public void setParentTask(Task parentTask) {
		this.parentTask = parentTask;
	}

	public List<Task> getSubTasks() {
		return subTasks;
	}

	public void setSubTasks(List<Task> subTasks) {
		this.subTasks = subTasks;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public Integer getOriginalEstimateMinutes() {
		return originalEstimateMinutes;
	}

	public void setOriginalEstimateMinutes(Integer originalEstimateMinutes) {
		this.originalEstimateMinutes = originalEstimateMinutes;
	}        
}
