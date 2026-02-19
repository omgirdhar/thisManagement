package com.application.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.application.management.model.Comment;
import com.application.management.model.Task;
import com.application.management.model.User;
import com.application.management.repo.CommentRepository;
import com.application.management.repo.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentService {

	private final TaskRepository taskRepository;
	private final CommentRepository commentRepository;
	private final UserService userService;
	
	CommentService(TaskRepository taskRepository, CommentRepository commentRepository, UserService userService) {
		this.taskRepository = taskRepository;
		this.commentRepository = commentRepository;
		this.userService = userService;
	}
	
	public List<Comment> getCommentsByTask(Task task){
		return commentRepository.findByTaskOrderByCreatedAtAsc(task);
	}
	
	@Transactional
	public Comment createComment(Long taskId, String content) {
		User loggedInUser = userService.getCurrentUser();
	    Task task = taskRepository.findById(taskId)
	            .orElseThrow(() -> new RuntimeException("Task not found"));

	    Comment comment = new Comment();
	    comment.setTask(task);
	    comment.setAuthor(loggedInUser);
	    comment.setContent(content);

	    return commentRepository.save(comment);
	}
	
	@Transactional
	public void updateComment(Long commentId, String content) {
		User loggedInUser = userService.getCurrentUser();
	    Comment comment = commentRepository.findById(commentId)
	            .orElseThrow(() -> new RuntimeException("Comment not found"));

	    if (!comment.getAuthor().getId().equals(loggedInUser.getId())) {
	        throw new RuntimeException("Unauthorized");
	    }

	    comment.setContent(content);
	}

	@Transactional
	public void deleteComment(Long commentId) {
		User loggedInUser = userService.getCurrentUser();
	    Comment comment = commentRepository.findById(commentId)
	            .orElseThrow(() -> new RuntimeException("Comment not found"));

	    if (!comment.getAuthor().getId().equals(loggedInUser.getId())) {
	        throw new RuntimeException("Unauthorized");
	    }

	    commentRepository.delete(comment);
	}

}
