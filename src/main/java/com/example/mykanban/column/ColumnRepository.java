package com.example.mykanban.column;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IColumnCrudRepository extends CrudRepository<Column, Integer> {
}
