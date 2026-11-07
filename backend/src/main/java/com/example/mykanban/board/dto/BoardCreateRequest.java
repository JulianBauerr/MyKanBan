package com.example.mykanban.board.dto;

import jakarta.validation.constraints.NotNull;

public record BoardCreateRequest(
    @NotNull
    String name
) {}


