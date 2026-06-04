package com.application.management.utils;

import java.util.List;

import org.jspecify.annotations.Nullable;

public class Enums {

	public enum Priority {
	    HIGH,
	    MEDIUM,
	    LOW;	   	   
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
