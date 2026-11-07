package com.example.mykanban.column.dto;

import com.example.mykanban.task.Task;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ColumnReorderRequest(
        @NotNull
        List<Integer> orderedTaskIds
) {
}
