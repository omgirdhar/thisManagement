package com.application.management.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.application.management.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskRestController {
	
	private final TaskService taskService;
	
	TaskRestController(TaskService taskService){
		this.taskService = taskService;		
	}

	@PatchMapping("/{taskId}")
	@ResponseBody
	public ResponseEntity<?> updateTask(@PathVariable Long taskId,
	                                    @RequestBody Map<String, Object> updates) {
	    taskService.updateTask(taskId, updates);
	    return ResponseEntity.ok().build();
	}
}
