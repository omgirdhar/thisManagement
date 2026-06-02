package com.application.management.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.application.management.dto.ProjectUserDTO;
import com.application.management.model.Project;
import com.application.management.model.ProjectMember;
import com.application.management.model.User;
import com.application.management.repo.ProjectMemberRepository;
import com.application.management.repo.ProjectRepository;
import jakarta.transaction.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMemberRepository projectMemberRepository;

    private final UserService userService;

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService, ProjectMemberRepository projectMemberRepository) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.projectMemberRepository = projectMemberRepository;
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

	@Override
	public List<ProjectUserDTO> getProjectUsers(Long projectId) {

		List<ProjectMember> assignedMembers = projectMemberRepository.findByProjectId(projectId);

		return assignedMembers.stream().map(pm -> pm.getUser()).distinct()
				.map(user -> new ProjectUserDTO(user.getId(), user.getFirstName() + " " + user.getLastName(), true))
				.toList();
	}

	@Override
	@Transactional
	public void assignUsers(Long projectId, List<Long> userIds) {

		Project project = projectRepository.findById(projectId).orElseThrow();

		projectMemberRepository.deleteByProject(project);

		for (long userId : userIds) {
			User user = userService.getUserById(userId);

			ProjectMember pm = new ProjectMember();
			pm.setProject(project);
			pm.setUser(user);
			pm.setActive(true);

			projectMemberRepository.save(pm);
		}
	}
	
	@Override
	public List<Project> getAllProjectsForUser() {
	    User currentUser = userService.getCurrentUser();
	    return projectMemberRepository.findProjectsByUserId(currentUser.getId());
	}

}
