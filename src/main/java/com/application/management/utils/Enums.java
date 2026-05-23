package com.application.management.utils;

import java.util.List;

import org.jspecify.annotations.Nullable;

public class Enums {

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

	public enum ProjectStatus {
	    ACTIVE,
	    INACTIVE,
	    ARCHIVED;

		public static List<ProjectStatus> getProjectStatus() {
	    	return List.of(ACTIVE,INACTIVE,ARCHIVED);
	    }
	}
}
