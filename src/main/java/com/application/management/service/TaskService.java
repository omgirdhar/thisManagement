package com.application.management.service;

import java.util.List;
import java.util.Map;

import com.application.management.model.Project;
import com.application.management.model.Task;

public interface TaskService {

	Task getTaskById(Long id);

	Task saveTask(Task task);

	void deleteTask(Long id);

	List<Task> getTasksForUser(Project project);

	List<Task> getChildTasksByParentTaskId(Task task);
	
	public void updateTask(Long taskId, Map<String, Object> updates);
}
