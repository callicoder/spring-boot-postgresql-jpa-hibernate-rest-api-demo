package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "todos")
public class Todos extends AuditModel {
    @Id
    @GeneratedValue(generator = "todos_generator")
    @SequenceGenerator(
            name = "todos_generator",
            sequenceName = "todos_sequence",
            initialValue = 1
    )
    private Long id;

    @Column(columnDefinition = "text")
    private String todo_list;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Users users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTodo_list() {
        return todo_list;
    }

    public void setTodo_list(String todo_list) {
        this.todo_list = todo_list;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
