package com.example.mykanban.task;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "task_name", nullable = false)
    private String name;
    @Column(name = "task_description")
    private String description;
    @Column(name = "task_done", nullable = false)
    private boolean done;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public Task(int id, String name, String description, boolean done) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.done = done;
    }

    public Task() {
    }


}
