package com.application.management.service;

import java.util.List;

import com.application.management.model.Project;

public interface ProjectService {

    Project saveProject(Project project);

    List<Project> getAllProjects();

    Project getProjectById(Long id);

    void deleteProject(Long id);
}
