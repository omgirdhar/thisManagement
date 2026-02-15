package com.application.management.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.management.model.Task;
import java.util.List;
import com.application.management.model.Project;
import com.application.management.model.User;
import com.application.management.utils.Enums.TaskType;




public interface TaskRepository extends JpaRepository<Task, Long>{
		
	List<Task> findByAssigneeAndCreatedByAndProjectAndParentTaskNull(User assignee, User createdBy, Project project);
	
	List<Task> findByParentTask(Task parentTask);
	
}
