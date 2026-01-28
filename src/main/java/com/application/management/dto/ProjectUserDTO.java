package com.application.management.dto;

public class ProjectUserDTO {
    private Long userId;
    private String name;
    private boolean assigned;
    
    public ProjectUserDTO(){
    	
    };
    
    public ProjectUserDTO(Long userId, String name, boolean assigned) {
        this.userId = userId;
        this.name = name;
        this.assigned = assigned;
    }
    
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAssigned() {
		return assigned;
	}
	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}
    
}
