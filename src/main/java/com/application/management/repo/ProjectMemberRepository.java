package com.application.management.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.management.model.ProjectMember;
import com.application.management.model.Project;


public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    List<ProjectMember> findByProjectId(Long projectId);

    boolean existsByProjectIdAndUserId(Long projectId, Long userId);
    
    void deleteByProject(Project project);
}
