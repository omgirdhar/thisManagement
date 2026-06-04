package com.application.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.management.model.Project;
import com.application.management.model.TaskStatus;
import com.application.management.model.TaskType;
import com.application.management.service.ProjectService;
import com.application.management.service.TaskStatusService;
import com.application.management.service.TaskTypeService;

@Controller
@RequestMapping("/projects/{projectId}/config")
public class ProjectConfigController {

	private final ProjectService projectService;
	private final TaskTypeService taskTypeService;
	private final TaskStatusService taskStatusService;

	public ProjectConfigController(ProjectService projectService, TaskTypeService taskTypeService, TaskStatusService taskStatusService) {
		this.projectService = projectService;
		this.taskTypeService = taskTypeService;
		this.taskStatusService = taskStatusService;
	}

	@GetMapping
	public String configPage(@PathVariable Long projectId, Model model) {

		Project project = projectService.getProjectById(projectId);

		model.addAttribute("project", project);

		model.addAttribute("taskTypes", taskTypeService.getTaskTypesByProject(projectId));
		model.addAttribute("statuses", taskStatusService.getByProject(projectId));

		model.addAttribute("newTaskType", new TaskType());
		model.addAttribute("newTaskStatus", new TaskStatus());

		return "project-config";
	}
	
	@PostMapping("/task-types")
	public String createTaskType(@PathVariable Long projectId,
	                             @RequestParam String taskTypeName) {

	    Project project = projectService.getProjectById(projectId);

	    TaskType type = new TaskType();
	    type.setName(taskTypeName);
	    type.setProject(project);

	    taskTypeService.save(type);

	    return "redirect:/projects/" + projectId + "/config";
	}
	
	@PostMapping("/statuses")
	public String createStatus(@PathVariable Long projectId, @RequestParam String taskStatusName, @RequestParam String taskStatusCode) {

		Project project = projectService.getProjectById(projectId);

		TaskStatus status = new TaskStatus();
		status.setName(taskStatusName);
		status.setCode(taskStatusCode);
		status.setProject(project);

		taskStatusService.save(status);

		return "redirect:/projects/" + projectId + "/config";
	}
	
	@GetMapping("/statuses/delete/{statusId}")
	public String deleteStatus(@PathVariable Long projectId, @PathVariable Long statusId) {

		taskStatusService.delete(statusId);

		return "redirect:/projects/" + projectId + "/config#statuses";
	}
}