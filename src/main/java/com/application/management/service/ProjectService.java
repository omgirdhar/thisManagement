package com.application.management.service;

import java.util.List;

import com.application.management.dto.ProjectUserDTO;
import com.application.management.model.Project;

public interface ProjectService {

    Project saveProject(Project project);

    List<Project> getAllProjects();

    Project getProjectById(Long id);

    void deleteProject(Long id);

	List<ProjectUserDTO> getProjectUsers(Long projectId);

	void assignUsers(Long projectId, List<Long> userIds);

	List<Project> getAllProjectsForUser();
}
