package com.example.mykanban.column.dto;

import jakarta.validation.constraints.NotNull;

public record ColumnCreateRequest(
        @NotNull
        String name
) {
}
