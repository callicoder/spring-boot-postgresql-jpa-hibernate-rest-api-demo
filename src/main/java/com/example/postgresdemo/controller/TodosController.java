package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Todos;
import com.example.postgresdemo.repository.TodosRepository;
import com.example.postgresdemo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TodosController {
    @Autowired
    private TodosRepository todosRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/users/{usersId}/todoList")
    public List<Todos> getTodosByUsersId(@PathVariable Long usersId) {
        return todosRepository.findByUsersId(usersId);
    }

    @PostMapping("/users/{usersId}/todoList")
    public Todos createTodos(@PathVariable Long usersId, @Valid @RequestBody Todos todos) {
        return usersRepository.findById(usersId)
                .map(users -> {
                    todos.setUsers(users);
                    return todosRepository.save(todos);
                }).orElseThrow(() -> new ResourceNotFoundException("Users not found with id " + usersId));
    }

    @PutMapping("/users/{usersId}/todoList/{todosId}")
    public Todos updateTodos(@PathVariable Long usersId, @PathVariable Long todosId, @Valid @RequestBody Todos todosReq) {
        if(!todosRepository.existsById(todosId)) {
            throw new ResourceNotFoundException("Todos not found with id " + usersId);
        }

        return todosRepository.findById(todosId)
                .map(todos -> {
                    todos.setTodo_list(todosReq.getTodo_list());
                    return todosRepository.save(todos);
                }).orElseThrow(() -> new ResourceNotFoundException("Todos not found with id " + todosId));
    }

    @DeleteMapping("/users/{usersId}/todoList/{todosId}")
    public ResponseEntity<?> deleteTodos(@PathVariable Long usersId, @PathVariable Long todosId) {
        if(!todosRepository.existsById(usersId)) {
            throw new ResourceNotFoundException("Todos not found with id " + todosId);
        }

        return todosRepository.findById(todosId)
                .map(todos -> {
                    todosRepository.delete(todos);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Todos not found with id " + todosId));

    }
}
