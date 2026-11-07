package com.example.mykanban.column;

import com.example.mykanban.board.Board;
import com.example.mykanban.board.BoardService;
import com.example.mykanban.column.dto.ColumnCreateRequest;
import com.example.mykanban.column.dto.ColumnReorderRequest;
import com.example.mykanban.column.dto.ColumnResponse;
import com.example.mykanban.column.dto.ColumnUpdateRequest;
import com.example.mykanban.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ColumService {
    private final ColumnRepository columnRepository;
    private final BoardService boardService;

    public ColumService(ColumnRepository columnRepository, BoardService boardService) {
        this.columnRepository = columnRepository;
        this.boardService = boardService;
    }

    public ColumnResponse createColumn(int boardId, ColumnCreateRequest request) {
        Column column = new Column();
        column.setName(request.name());
        Board board = boardService.getBoardEntity(boardId);
        board.getColumns().add(column);
        Board savedBoard = boardService.updateBoardEntity(board);
        Column savedColumn = savedBoard.getColumns().getLast();
        return ColumnResponse.fromEntity(savedColumn);
    }

    public ColumnResponse updateColumn(int id, ColumnUpdateRequest request) {
        Column existingColumn = columnRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Column with id " + id + " not found"));
        existingColumn.setName(request.Name());
        existingColumn.setTasks(request.taskList());

        Column savedColumn = columnRepository.save(existingColumn);
        return ColumnResponse.fromEntity(savedColumn);
    }

    public ColumnResponse removeColumn(int id) {
        Column column = columnRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Column with id " + id + " not found"));
        columnRepository.delete(column);
        return ColumnResponse.fromEntity(column);
    }

    public ColumnResponse updateColumnSorting(int columnId, ColumnReorderRequest request) {
        Column existingColumn = columnRepository.findById(columnId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Column with id " + columnId + " not found"));

        List<Task> currentTasks = existingColumn.getTasks();
        Map<Integer, Task> taskMap = currentTasks.stream().collect(Collectors.toMap(Task::getId, task -> task));

        currentTasks.clear();

        for (Integer taskId : request.orderedTaskIds()) {
            Task task = taskMap.get(taskId);
            if (task == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task with id " + taskId + " does not belong to this column.");
            }
            currentTasks.add(task);
        }

        Column savedColumn = columnRepository.save(existingColumn);
        return ColumnResponse.fromEntity(savedColumn);
    }

    public Column getColumnEntity(int columnId) {
        return columnRepository.findById(columnId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Column with id " + columnId + " not found"));
    }

    public Column updateColumnEntity(Column column) {
        Column existingColumn = columnRepository.findById(column.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Column with id " + column.getId() + " not found"));
        existingColumn.setName(column.getName());
        existingColumn.setTasks(column.getTasks());

        return columnRepository.save(existingColumn);
    }
}
