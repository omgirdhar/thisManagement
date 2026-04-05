package com.application.management.utils;

import java.util.List;

public class Enums {

	public enum TaskType {
	    EPIC,
	    STORY,
	    TASK,
	    SUB_TASK
	}
	
	public enum Priority {
	    HIGH,
	    MEDIUM,
	    LOW;	   	   
	}
	
	public enum Status{
		TODO,PENDING,IN_PROGRESS,DONE;
		 public static List<Status> getAllStatuses() {
		    	return List.of(TODO,PENDING,IN_PROGRESS,DONE);
		    }
	}

}
