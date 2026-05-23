package com.application.management.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.management.dto.ProjectUserDTO;
import com.application.management.dto.TaskEditDTO;
import com.application.management.dto.TaskTypeDTO;
import com.application.management.model.Comment;
import com.application.management.model.Project;
import com.application.management.model.Task;
import com.application.management.model.TaskType;
import com.application.management.model.User;
import com.application.management.service.CommentService;
import com.application.management.service.ProjectService;
import com.application.management.service.TaskService;
import com.application.management.service.TaskTypeService;
import com.application.management.service.UserService;
import com.application.management.utils.TimeFormatUtils;
import com.application.management.utils.Enums;

@Controller
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final CommentService commentService;
    private final UserService userService;
    private final TaskTypeService taskTypeService; 

    TaskController(ProjectService projectService, TaskService taskService, CommentService commentService, UserService userService, TaskTypeService taskTypeService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.commentService = commentService;
		this.userService = userService;
		this.taskTypeService = taskTypeService;
    }

    @GetMapping
    public String getTasks(@PathVariable Long projectId, Model model) {

        Project project = projectService.getProjectById(projectId);
        model.addAttribute("project", project);

        List<ProjectUserDTO> projectUsers = projectService.getProjectUsers(projectId);
        model.addAttribute("projectUsers", projectUsers);
        
        List<Task> allTasks = taskService.getTasksForUser(project);
        model.addAttribute("taskList", allTasks);

//        List<TaskType> taskTypes = taskTypeService.getTaskTypesByProject(projectId);
//        model.addAttribute("taskTypes", taskTypes);
        List<TaskTypeDTO> taskTypes = taskTypeService.getTaskTypesByProject(projectId)
        	    .stream()
        	    .map(t -> new TaskTypeDTO(t.getId(), t.getName()))
        	    .toList();

        	model.addAttribute("taskTypes", taskTypes);
        
        Task newTask = new Task();
//        newTask.setTaskType(TaskType.TASK);
        model.addAttribute("newTask", newTask);
        model.addAttribute("statuses", Enums.Status.getAllStatuses());
        return "usersProjectTasks";
    }
    
    @PostMapping("/save")
    public String saveTask(@PathVariable Long projectId,
                           @RequestParam(required = false) Long parentTaskId,
                           @RequestParam(required = false) String estimateInput,
                           @ModelAttribute Task task) {

    	Project project = projectService.getProjectById(projectId);
        task.setProject(project);
        if (task.getTaskType() != null && task.getTaskType().getId() != null) {
            TaskType type = taskTypeService.getTaskTypeById(task.getTaskType().getId());
            if(type != null) {
                if (!type.getProject().getId().equals(project.getId())) {
                    throw new RuntimeException("Invalid TaskType for this Project");
                }
            }else {
            	throw new RuntimeException("TaskType not found");
            }
            task.setTaskType(type);
        }
        
     // FIX: estimate handling
        if (estimateInput != null && !estimateInput.isBlank()) {
            int totalMinutes = TimeFormatUtils.parseEstimateToMinutes(estimateInput);
            task.setOriginalEstimateMinutes(totalMinutes);
        }
        
        if (parentTaskId != null) {
            Task parent = taskService.getTaskById(parentTaskId);
            task.setParentTask(parent);
        }
        taskService.saveTask(task);
        return "redirect:/projects/" + projectId + "/tasks";
    }

    @PostMapping("/update")
    public String updateTask(@PathVariable Long projectId,
                             @ModelAttribute Task taskForm,
                             @RequestParam(required = false) Long parentTaskId,
                             @RequestParam(required = false) String estimateInput) {

        Task existingTask = taskService.getTaskById(taskForm.getId());

        existingTask.setTitle(taskForm.getTitle());
        existingTask.setDescription(taskForm.getDescription());
        existingTask.setStatus(taskForm.getStatus());
        existingTask.setStartDate(taskForm.getStartDate());
        existingTask.setDueDate(taskForm.getDueDate());
        if (taskForm.getTaskType() != null && taskForm.getTaskType().getId() != null) {
            TaskType type = taskTypeService.getTaskTypeById(taskForm.getTaskType().getId());
            if(type != null) {
            	if (!type.getProject().getId().equals(existingTask.getProject().getId())) {
                    throw new RuntimeException("Invalid TaskType for this Project");
                }
            }else {
            	throw new RuntimeException("TaskType not found");
            }
            existingTask.setTaskType(type);
        }
        existingTask.setPriority(taskForm.getPriority());

        int totalMinutes = TimeFormatUtils.parseEstimateToMinutes(estimateInput);
        existingTask.setOriginalEstimateMinutes(totalMinutes);
	    
        if (taskForm.getAssignee() != null && taskForm.getAssignee().getId() != null) {
            User managedUser = userService.getUserById(taskForm.getAssignee().getId());
            existingTask.setAssignee(managedUser);
        } else {
            existingTask.setAssignee(null);
        }

        if (parentTaskId != null) {
            Task parent = taskService.getTaskById(parentTaskId);
            existingTask.setParentTask(parent);
        } else {
            existingTask.setParentTask(null);
        }

        taskService.saveTask(existingTask);

        return "redirect:/projects/" + projectId + "/tasks";
    }
    
    @GetMapping("/{taskId}")
    @ResponseBody
    public ResponseEntity<TaskEditDTO> getTask(@PathVariable Long projectId,
                                               @PathVariable Long taskId) {

        Task task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(new TaskEditDTO(task));
    }

    // Dummy delete
    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        System.out.println("Deleted Task: " + taskId + " from project: " + projectId);
        return "redirect:/projects/" + projectId + "/tasks";
    }
    
    @GetMapping("/details/{taskId}")
    public String taskDetails(@PathVariable Long projectId,
                              @PathVariable Long taskId, Model model) {

    	Project project = projectService.getProjectById(projectId);
        model.addAttribute("project", project);
        
        Task currentTask = taskService.getTaskById(taskId);
     // FIXED: subtask logic
        if (currentTask.getParentTask() == null) {
            List<Task> childTasks = taskService.getChildTasksByParentTaskId(currentTask);
            model.addAttribute("childTasks", childTasks);
        }
		List<Comment> comments = commentService.getCommentsByTask(currentTask);

        model.addAttribute("comments", comments);
        model.addAttribute("currentTask", currentTask);
        model.addAttribute("statuses", Enums.Status.getAllStatuses());
        return "taskDetails";
    }

}