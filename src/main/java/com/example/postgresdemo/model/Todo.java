package com.example.postgresdemo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table (name = "todo")
public class Todo extends AuditModel {
    @Id
    @GeneratedValue (generator = "todo_generator")
    @SequenceGenerator(
            name = "todo_generator",
            sequenceName = "todo_sequence",
            initialValue = 2000
    )
    private Long id;

    @Column(columnDefinition = "text")
    private String todos;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore

    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getTodos() {
        return todos;
    }

    public void setTodos(String todos) {
        this.todos = todos;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }
}
