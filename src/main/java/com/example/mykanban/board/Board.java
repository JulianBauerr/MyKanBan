package com.example.mykanban.board;

import com.example.mykanban.column.Column;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @jakarta.persistence.Column(name = "board_name", nullable = false, length = 100)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "board_id")
    private List<Column> columns = new ArrayList<>();

    public Board() {
    }

    public Board(Integer id, String name, List<Column> columns) {
        this.id = id;
        this.name = name;
        this.columns = columns;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
