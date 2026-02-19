package com.application.management.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.application.management.model.Comment;
import com.application.management.service.CommentService;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class CommentRestController {
	private final CommentService commentService;
	
	public CommentRestController(CommentService commentService) {
		this.commentService = commentService;
	}
	
    @PostMapping("/{taskId}/comments")
    @ResponseBody
	public ResponseEntity<?> addComment(@PathVariable Long taskId, @RequestBody Map<String, String> payload) {
		Comment comment = commentService.createComment(taskId, payload.get("content"));
		return ResponseEntity.ok(comment.getId());
	}
    
    @DeleteMapping("/comments/{commentId}")
    @ResponseBody
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
		commentService.deleteComment(commentId);
		return ResponseEntity.ok("Comment Deleted.");
	}
    
    @PatchMapping("/comments/{commentId}")
    @ResponseBody
    public ResponseEntity<?> updateComment(@PathVariable Long commentId,
                                               @RequestBody Map<String, String> payload) {
    	commentService.updateComment(commentId, payload.get("content"));
		return ResponseEntity.ok(commentId);
    }

}
