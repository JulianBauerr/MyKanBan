package com.example.mykanban.task.dto;

import com.example.mykanban.task.Task;
import jakarta.validation.constraints.NotNull;

import javax.naming.Name;

public record TaskResponse(
        @NotNull
        int id,
        @NotNull
        String name,
        String description,
        boolean done
) {
        public static TaskResponse fromEntity(Task task){
                return new TaskResponse(
                        task.getId(),
                        task.getName(),
                        task.getDescription(),
                        task.isDone()
                );
        }
}
