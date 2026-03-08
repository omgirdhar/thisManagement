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

    @PatchMapping("/{taskId}/description")
    @ResponseBody
    public ResponseEntity<?> updateDescription(@PathVariable Long taskId,
                                               @RequestBody Map<String, String> payload) {

        String description = payload.get("description");
        taskService.updateDescription(taskId, description);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{taskId}/priority")
    @ResponseBody
    public ResponseEntity<?> updatePriority(@PathVariable Long taskId,
                                            @RequestBody Map<String, String> payload) {
        String priorityValue = payload.get("priority");
        taskService.updatePriority(taskId, priorityValue);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{taskId}/due-date")
    @ResponseBody
    public ResponseEntity<?> updateDueDate(@PathVariable Long taskId,
                                           @RequestBody Map<String, String> payload) {
        String dueDateValue = payload.get("dueDate");
        taskService.updateDueDate(taskId, dueDateValue);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{taskId}/start-date")
    @ResponseBody
    public ResponseEntity<?> updateStartDate(@PathVariable Long taskId,
                                             @RequestBody Map<String, String> payload) {
        String startDateValue = payload.get("startDate");
        taskService.updateStartDate(taskId, startDateValue);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{taskId}/estimate")
    @ResponseBody
    public ResponseEntity<?> updateEstimate(@PathVariable Long taskId,
                                            @RequestBody Map<String, String> payload) {
        String estimateValue = payload.get("estimate");
        taskService.updateEstimate(taskId, estimateValue);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{taskId}/status")
    @ResponseBody
    public ResponseEntity<?> updateStatus(@PathVariable Long taskId,
                                            @RequestBody Map<String, String> payload) {
        String statusValue = payload.get("status");
        taskService.updateStatus(taskId, statusValue);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{taskId}/assignee")
    @ResponseBody
    public ResponseEntity<?> updateAssignee(@PathVariable Long taskId,
                                            @RequestBody Map<String, String> payload) {
        String assigneeIdStr = payload.get("assigneeId");
    	Long assigneeId;
    	if(assigneeIdStr == "") assigneeId = null;
    	else assigneeId = Long.valueOf(assigneeIdStr);
        taskService.updateAssignee(taskId, assigneeId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{taskId}/title")
    @ResponseBody
    public ResponseEntity<?> updateTitle(@PathVariable Long taskId,
                                            @RequestBody Map<String, String> payload) {
    	String titleValue = payload.get("title");
        taskService.updateTitle(taskId, titleValue);
        return ResponseEntity.ok().build();
    }
}
