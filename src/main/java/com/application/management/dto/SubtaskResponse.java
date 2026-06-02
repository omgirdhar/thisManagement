package com.application.management.dto;

public record SubtaskResponse(
        Long id,
        String title,
        String status
) {}