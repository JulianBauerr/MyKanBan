package com.example.mykanban.task;

import com.example.mykanban.task.dto.TaskCreateRequest;
import com.example.mykanban.task.dto.TaskMoveRequest;
import com.example.mykanban.task.dto.TaskResponse;
import com.example.mykanban.task.dto.TaskUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/columns/{columnId}/tasks")
    public ResponseEntity<TaskResponse> createTask(@PathVariable int columnId, @Valid @RequestBody TaskCreateRequest request) {
        TaskResponse response = taskService.createTask(columnId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable int id, @Valid @RequestBody TaskUpdateRequest request) {
        TaskResponse response = taskService.updateTask(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable int id) {
        TaskResponse response = taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/tasks/{columnId}/move")
    public ResponseEntity<TaskResponse> moveTask(@PathVariable int columnId, @Valid @RequestBody TaskMoveRequest request) {
        TaskResponse response = taskService.moveTask(columnId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
