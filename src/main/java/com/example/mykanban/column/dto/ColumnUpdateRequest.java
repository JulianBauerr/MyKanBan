package com.example.mykanban.column.dto;

import com.example.mykanban.task.Task;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ColumUpdateRequest(
        @NotNull
        String Name,
        List<Task> taskList,
        List<Task> sorting
) {
}
