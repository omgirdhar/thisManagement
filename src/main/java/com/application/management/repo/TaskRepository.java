package com.application.management.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.application.management.model.Task;
import java.util.List;
import com.application.management.model.Project;
import com.application.management.model.User;
import com.application.management.utils.Enums.TaskType;




public interface TaskRepository extends JpaRepository<Task, Long>{
	
	@Query("""
			SELECT t FROM Task t
			WHERE t.project = :project
			AND t.parentTask IS NULL
			AND (t.assignee = :user OR t.createdBy = :user)
			""")
	List<Task> findUserTasks(User user, Project project);
	
	List<Task> findByParentTask(Task parentTask);
	
}
