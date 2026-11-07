package com.example.mykanban.task.dto;

import jakarta.validation.constraints.NotNull;

public record TaskCreateRequest(
        @NotNull
        String name,
        String description,
        @NotNull
        boolean done
) {
}
