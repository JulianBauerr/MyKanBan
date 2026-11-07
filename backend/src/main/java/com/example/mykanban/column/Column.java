package com.example.mykanban.column;

import com.example.mykanban.task.Task;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "columns")
public class Column {
    @Id
    @GeneratedValue
    private int id;
    @jakarta.persistence.Column(name = "column_name", nullable = false)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "column_id")
    @OrderColumn(name = "task_position")
    private List<Task> tasks = new ArrayList<>();

    public Column(int id, String name, List<Task> tasks, List<Task> sorting) {
        this.id = id;
        this.name = name;
        this.tasks = tasks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }


    public Column() {
    }
}
