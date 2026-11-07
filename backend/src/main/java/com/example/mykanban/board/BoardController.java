package com.example.mykanban.board;

import com.example.mykanban.board.dto.BoardCreateRequest;
import com.example.mykanban.board.dto.BoardResponse;
import com.example.mykanban.board.dto.BoardUpdateRequest;
import com.example.mykanban.board.dto.MultipleBoardResponse;
import com.example.mykanban.column.dto.ColumnCreateRequest;
import com.example.mykanban.column.dto.ColumnResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("")
    public ResponseEntity<BoardResponse> newBoard(@Valid @RequestBody BoardCreateRequest request) {
        BoardResponse response = boardService.createBoard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable int id) {
        BoardResponse response = boardService.getBoard(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("")
    public ResponseEntity<MultipleBoardResponse> getAllBoards() {
        MultipleBoardResponse response = boardService.getAllBoards();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable int id, @Valid @RequestBody BoardUpdateRequest request) {
        BoardResponse response = boardService.updateBoard(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BoardResponse> removeBoard(@PathVariable int id) {
        BoardResponse response = boardService.removeBoard(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
