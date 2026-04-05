package com.application.management.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.management.model.Comment;
import com.application.management.model.Task;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTaskOrderByCreatedAtAsc(Task task);
}