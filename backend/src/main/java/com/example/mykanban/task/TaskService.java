package com.example.mykanban.task;

import com.example.mykanban.column.ColumService;
import com.example.mykanban.column.Column;
import com.example.mykanban.task.dto.TaskCreateRequest;
import com.example.mykanban.task.dto.TaskMoveRequest;
import com.example.mykanban.task.dto.TaskResponse;
import com.example.mykanban.task.dto.TaskUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ColumService columService;

    public TaskService(TaskRepository taskRepository, ColumService columService) {
        this.taskRepository = taskRepository;
        this.columService = columService;
    }


    public TaskResponse createTask(int columnId, TaskCreateRequest request) {
        Task task = new Task();
        task.setName(request.name());
        task.setDescription(request.description());
        task.setDone(request.done());

        Column column = columService.getColumnEntity(columnId);
        column.getTasks().addLast(task);
        Column savedColumn = columService.updateColumnEntity(column);
        Task savedTask = savedColumn.getTasks().getLast();
        return TaskResponse.fromEntity(savedTask);
    }

    public TaskResponse updateTask(int id, TaskUpdateRequest request) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException((HttpStatus.NOT_FOUND), "Task wit id " + id + " not found"));
        existingTask.setName(request.name());
        existingTask.setDescription(request.description());
        existingTask.setDone(request.done());
        Task savedTask = taskRepository.save(existingTask);
        return TaskResponse.fromEntity(savedTask);
    }

    public TaskResponse deleteTask(int id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException((HttpStatus.NOT_FOUND), "Task wit id " + id + " not found"));
        taskRepository.delete(task);
        return TaskResponse.fromEntity(task);
    }

    public TaskResponse moveTask(int columnId, TaskMoveRequest request) {
        Task task = taskRepository.findById(request.taskId())
                .orElseThrow(() -> new ResponseStatusException((HttpStatus.NOT_FOUND), "Task wit id " + request.taskId() + " not found"));;
        Column oldColumn = columService.getColumnEntity(columnId);
        oldColumn.getTasks().remove(task);
        columService.updateColumnEntity(oldColumn);
        Column newColumn = columService.getColumnEntity(request.moveToColumnId());
        newColumn.getTasks().addLast(task);
        newColumn.getTasks().addLast(task);
        Column savedColumn = columService.updateColumnEntity(newColumn);
        return TaskResponse.fromEntity(savedColumn.getTasks().getLast());
    }
}
