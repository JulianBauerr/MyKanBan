package com.example.mykanban.board.dto;

import com.example.mykanban.board.Board;
import com.example.mykanban.column.dto.ColumnResponse;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BoardResponse(
    @NotNull
    Integer id,
    @NotNull
    String name,
    List<ColumnResponse> columns
) {
    public static BoardResponse fromEntity(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getName(),
                board.getColumns().stream().map(ColumnResponse::fromEntity).toList()
        );
    }
}
