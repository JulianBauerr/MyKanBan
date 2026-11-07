package com.example.mykanban.column;

import com.example.mykanban.column.dto.ColumnCreateRequest;
import com.example.mykanban.column.dto.ColumnReorderRequest;
import com.example.mykanban.column.dto.ColumnResponse;
import com.example.mykanban.column.dto.ColumnUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ColumnController {
    private final ColumService columService;

    public ColumnController(ColumService columService) {
        this.columService = columService;
    }

    @PostMapping("/boards/{boardId}/columns")
    public ResponseEntity<ColumnResponse> createColumn(@PathVariable int boardId, @Valid @RequestBody ColumnCreateRequest request){
        ColumnResponse response = columService.createColumn(boardId ,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/columns/{id}")
    public ResponseEntity<ColumnResponse> updateColumn(@PathVariable int id, @Valid @RequestBody ColumnUpdateRequest request){
        ColumnResponse response = columService.updateColumn(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/columns/{id}")
    public ResponseEntity<ColumnResponse> deleteColumn(@PathVariable int id) {
        ColumnResponse response = columService.removeColumn(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/columns/{columnId}/tasks/reorder")
    public ResponseEntity<ColumnResponse> updateColumnSorting(@PathVariable int columnId, @Valid @RequestBody ColumnReorderRequest request) {
        ColumnResponse response = columService.updateColumnSorting(columnId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
