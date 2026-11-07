package com.example.mykanban.column.dto;

import com.example.mykanban.column.Column;
import com.example.mykanban.task.Task;
import com.example.mykanban.task.dto.TaskResponse;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ColumnResponse(
        @NotNull
        int id,
        @NotNull
        String name,
        List<TaskResponse>tasks
) {
        public static ColumnResponse fromEntity(Column column){
                return new ColumnResponse(
                        column.getId(),
                        column.getName(),
                        column.getTasks().stream().map(TaskResponse::fromEntity).toList()
                );
        }
}
