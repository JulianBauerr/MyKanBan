package com.example.mykanban.board.dto;

import com.example.mykanban.column.Column;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BoardUpdateRequest(
    @NotNull
    String name,
    List<Column> columns
) {}
