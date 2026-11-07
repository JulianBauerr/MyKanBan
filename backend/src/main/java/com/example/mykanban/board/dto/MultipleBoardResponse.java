package com.example.mykanban.board.dto;

import java.util.List;

public record MultipleBoardResponse(
        List<BoardResponse> boardList
) {
}
