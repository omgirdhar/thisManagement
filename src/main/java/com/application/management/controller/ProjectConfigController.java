package com.application.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.management.model.Project;
import com.application.management.model.TaskType;
import com.application.management.service.ProjectService;
import com.application.management.service.TaskTypeService;

@Controller
@RequestMapping("/projects/{projectId}/config")
public class ProjectConfigController {

	private final ProjectService projectService;
	private final TaskTypeService taskTypeService;

	public ProjectConfigController(ProjectService projectService, TaskTypeService taskTypeService) {
		this.projectService = projectService;
		this.taskTypeService = taskTypeService;
	}

	@GetMapping
	public String configPage(@PathVariable Long projectId, Model model) {

		Project project = projectService.getProjectById(projectId);

		model.addAttribute("project", project);

		model.addAttribute("taskTypes", taskTypeService.getTaskTypesByProject(projectId));

		model.addAttribute("newTaskType", new TaskType());

		return "project-config";
	}
	
	@PostMapping("/task-types")
	public String createTaskType(@PathVariable Long projectId,
	                             @RequestParam String name) {

	    Project project = projectService.getProjectById(projectId);

	    TaskType type = new TaskType();
	    type.setName(name);
	    type.setProject(project);

	    taskTypeService.save(type);

	    return "redirect:/projects/" + projectId + "/config";
	}
}