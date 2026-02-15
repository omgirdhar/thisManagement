package com.application.management.service;

import java.util.List;

import com.application.management.model.Project;
import com.application.management.model.Task;
import com.application.management.utils.Enums.TaskType;

public interface TaskService {

	Task getTaskById(Long id);

	Task saveTask(Task task);

	void deleteTask(Long id);

	List<Task> getTasksForUser(Project project);

	List<Task> getChildTasksByParentTaskId(Task task);
	
	void updateDescription(Long taskId, String description);
}
