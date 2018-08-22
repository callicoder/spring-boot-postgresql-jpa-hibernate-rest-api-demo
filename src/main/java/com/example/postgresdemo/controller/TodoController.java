package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Todo;
import com.example.postgresdemo.repository.TodoRepository;
import com.example.postgresdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping ("/users/{userId}/todos")
    public List<Todo> getTodoByUserId(@PathVariable Long userId) {
        return todoRepository.findByUserId(userId);
    }

    @PostMapping("/users/{userId}/todos")
    public Todo addTodo(@PathVariable Long userId,
                        @Valid @RequestBody Todo todo) {
        return userRepository.findById(userId)
                .map(user -> {
                    todo.setUser(user);
                    return todoRepository.save(todo);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id" + userId));
    }

    @PutMapping ("users/{userId}/todos/{todoId}")
    public Todo updateTodo (@PathVariable Long userId,
                            @PathVariable Long todoId,
                            @Valid @RequestBody Todo todosRequest
                            ) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id" + userId);
        }

        return todoRepository.findById(userId)
                .map(todo -> {
                    todo.setTodos(todosRequest.getTodos());
                    return todoRepository.save(todo);
                }).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + todoId));
    }

    @DeleteMapping("/users/{userId}/todo/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long userId,
                                        @PathVariable Long todoId) {
        if (!todoRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }

        return todoRepository.findById(todoId)
                .map(todo -> {
                    todoRepository.delete(todo);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id" + todoId));
    }
}
