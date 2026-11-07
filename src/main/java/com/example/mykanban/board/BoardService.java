package com.example.mykanban.board;

import com.example.mykanban.board.dto.BoardCreateRequest;
import com.example.mykanban.board.dto.BoardResponse;
import com.example.mykanban.board.dto.BoardUpdateRequest;
import com.example.mykanban.board.dto.MultipleBoardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardResponse createBoard(BoardCreateRequest boardCreateRequest){
        Board board = new Board();
        board.setName(boardCreateRequest.name());

        Board boardResponse = boardRepository.save(board);
        return new BoardResponse(boardResponse.getId(), boardResponse.getName(), List.of());
    }

    public BoardResponse getBoard(int id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board with id " + id + " not found"));

        return BoardResponse.fromEntity(board);
    }

    public MultipleBoardResponse getAllBoards() {
        List<Board> boardList;
        boardList = boardRepository.findAll();
        return new MultipleBoardResponse(boardList.stream().map(BoardResponse::fromEntity).toList());
    }

    public BoardResponse updateBoard(int id, BoardUpdateRequest request) {
        Board existingBoard = boardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board with id " +id+ " not found"));
        existingBoard.setName(request.name());
        existingBoard.setColumns(request.columns());
        Board savedBoard = boardRepository.save(existingBoard);
        return BoardResponse.fromEntity(savedBoard);
    }

    public BoardResponse removeBoard(int id) {
        Board board = boardRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board with id " +id+ " not found"));
        boardRepository.deleteById(id);
        return BoardResponse.fromEntity(board);
    }

    public Board getBoardEntity(int id) {
        return boardRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board with id " +id+ " not found"));
    }

    public Board updateBoardEntity(Board board) {
        Board existingBoard = boardRepository.findById(board.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board with id " +board.getId()+ " not found"));
        existingBoard.setName(board.getName());
        existingBoard.setColumns(board.getColumns());
        return boardRepository.save(existingBoard);
    }

}
