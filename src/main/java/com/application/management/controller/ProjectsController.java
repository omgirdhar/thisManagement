package com.application.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.application.management.dto.ProjectUserDTO;
import com.application.management.model.Project;
import com.application.management.service.ProjectService;

@Controller
public class ProjectsController {

    private final ProjectService projectService;

    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public ModelAndView projectsPage() {
        ModelAndView view = new ModelAndView("projects");
        view.addObject("newProject", new Project());
        view.addObject("projectList", projectService.getAllProjects());
        return view;
    }

    @PostMapping("/saveProject")
    public String saveProject(@ModelAttribute("newProject") Project project) {
        projectService.saveProject(project);
        return "redirect:/projects";
    }

    @PostMapping("/updateProject")
    public String updateProject(@ModelAttribute Project project) {
        projectService.saveProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/deleteProject/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }

    @GetMapping("/updateProject/{id}")
    @ResponseBody
    public Project getProject(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }
    
    @GetMapping("/projects/{projectId}/users")
    @ResponseBody
    public List<ProjectUserDTO> getProjectUsers(@PathVariable Long projectId) {
        return projectService.getProjectUsers(projectId);
    }

    @PostMapping("/projects/{projectId}/users")
    public ResponseEntity<Void> assignUsers(
            @PathVariable Long projectId,
            @RequestBody List<Long> userIds) {

        projectService.assignUsers(projectId, userIds);
        return ResponseEntity.ok().build();
    }

}
