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
import com.application.management.model.Comment;
import com.application.management.model.Project;
import com.application.management.model.Task;
import com.application.management.model.User;
import com.application.management.service.CommentService;
import com.application.management.service.ProjectService;
import com.application.management.service.TaskService;
import com.application.management.utils.Enums.TaskType;


@Controller
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final CommentService commentService;

    TaskController(ProjectService projectService, TaskService taskService, CommentService commentService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.commentService = commentService;
    }

    @GetMapping
    public String getTasks(@PathVariable Long projectId, Model model) {

        Project project = projectService.getProjectById(projectId);
        model.addAttribute("project", project);

        List<ProjectUserDTO> projectUsers = projectService.getProjectUsers(projectId);
        model.addAttribute("projectUsers", projectUsers);
        
        List<Task> allTasks = taskService.getTasksForUser(project);
        model.addAttribute("taskList", allTasks);

        Task newTask = new Task();
        newTask.setTaskType(TaskType.TASK);
        model.addAttribute("newTask", newTask);

        return "usersProjectTasks";
    }
    
    @PostMapping("/save")
    public String saveTask(@PathVariable Long projectId,
                           @RequestParam(required = false) Long parentTaskId,
                           @ModelAttribute Task task) {

        if (parentTaskId != null) {
            Task parent = taskService.getTaskById(parentTaskId);
            task.setParentTask(parent);
        }
        task.setProject(projectService.getProjectById(projectId));
        taskService.saveTask(task);
        return "redirect:/projects/" + projectId + "/tasks";
    }

    @PostMapping("/update")
    public String updateTask(@PathVariable Long projectId,
                             @ModelAttribute Task task) {

        System.out.println("====== UPDATE TASK ======");
        System.out.println("ID: " + task.getId());
        System.out.println("Type: " + task.getTaskType());
        System.out.println("Title: " + task.getTitle());
        System.out.println("=========================");

        return "redirect:/projects/" + projectId + "/tasks";
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
        if(currentTask.getTaskType() != TaskType.SUB_TASK) {
        	List<Task> childTasks = taskService.getChildTasksByParentTaskId(currentTask);
        	model.addAttribute("childTasks", childTasks);
        }
		List<Comment> comments = commentService.getCommentsByTask(currentTask);

        model.addAttribute("comments", comments);
        model.addAttribute("currentTask", currentTask);
        return "taskDetails";
    }

    @GetMapping("/createSubTask")
    public String createSubTask(@RequestParam Long parentId,
                                @RequestParam Long projectId,
                                Model model) {

        // Dummy parent task
        Task parent = new Task();
        parent.setId(parentId);
        parent.setTitle("Parent Task Title");

        Task newTask = new Task();
        newTask.setParentTask(parent);
        newTask.setTaskType(TaskType.SUB_TASK); // automatically set

        model.addAttribute("newTask", newTask);

        // Use the same create modal page if you want
        model.addAttribute("projectId", projectId);
        return "usersProjectTasks"; // or redirect to modal
    }
    
    @PatchMapping("/{taskId}/description")
    @ResponseBody
    public ResponseEntity<?> updateDescription(@PathVariable Long taskId,
                                               @RequestBody Map<String, String> payload) {

        String description = payload.get("description");
        taskService.updateDescription(taskId, description);

        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{taskId}/priority")
    @ResponseBody
    public ResponseEntity<?> updatePriority(@PathVariable Long taskId,
                                            @RequestBody Map<String, String> payload) {

        String priorityValue = payload.get("priority");

        taskService.updatePriority(taskId, priorityValue);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{taskId}/due-date")
    @ResponseBody
    public ResponseEntity<?> updateDueDate(@PathVariable Long taskId,
                                           @RequestBody Map<String, String> payload) {

        String dueDateValue = payload.get("dueDate");

        taskService.updateDueDate(taskId, dueDateValue);

        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{taskId}/start-date")
    @ResponseBody
    public ResponseEntity<?> updateStartDate(@PathVariable Long taskId,
                                             @RequestBody Map<String, String> payload) {

        String startDateValue = payload.get("startDate");

        taskService.updateStartDate(taskId, startDateValue);

        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{taskId}/estimate")
    @ResponseBody
    public ResponseEntity<?> updateEstimate(@PathVariable Long taskId,
                                            @RequestBody Map<String, String> payload) {

        String estimateValue = payload.get("estimate");

        taskService.updateEstimate(taskId, estimateValue);

        return ResponseEntity.ok().build();
    }



}