package com.example.mykanban.task.dto;

import jakarta.validation.constraints.NotNull;

public record TaskMoveRequest(
        @NotNull
        int taskId,
        @NotNull
        int moveToColumnId
) {
}
