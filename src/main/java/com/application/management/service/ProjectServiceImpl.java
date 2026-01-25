package com.application.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.application.management.model.Project;
import com.application.management.repo.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
