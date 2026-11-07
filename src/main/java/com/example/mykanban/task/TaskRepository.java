package com.example.mykanban.task;

import org.springframework.data.repository.CrudRepository;

public interface ITaskCrudRepository extends CrudRepository<Task, Integer> {
}
