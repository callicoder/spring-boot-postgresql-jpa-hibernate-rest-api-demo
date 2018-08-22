package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Todo;
import com.example.postgresdemo.repository.TodoRepository;
import com.example.postgresdemo.repository.MemberRepository;
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
    private MemberRepository memberRepository;

    @GetMapping("/member/{memberId}/todo")
    public List<Todo> getTodoByMemberId(@PathVariable Long memberId) {
        return todoRepository.findByMemberId(memberId);
    }

    @PostMapping("/member/{memberId}/todo")
    public Todo addTodo(@PathVariable Long memberId,
                          @Valid @RequestBody Todo todo) {
        return memberRepository.findById(memberId)
                .map(member -> {
                    todo.setMember(member);
                    return todoRepository.save(todo);
                }).orElseThrow(() -> new ResourceNotFoundException("Member not found with id " + memberId));
    }

    @PutMapping("/member/{memberId}/todo/{todoId}")
    public Todo updateTodo(@PathVariable Long memberId,
                             @PathVariable Long todoId,
                             @Valid @RequestBody Todo todoRequest) {
        if(!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member not found with id " + memberId);
        }

        return todoRepository.findById(todoId)
                .map(todo -> {
                    todo.setTodo(todoRequest.getTodo());
                    return todoRepository.save(todo);
                }).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + todoId));
    }

    @DeleteMapping("/member/{memberId}/todo/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long memberId,
                                          @PathVariable Long todoId) {
        if(!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member not found with id " + memberId);
        }

        return todoRepository.findById(todoId)
                .map(todo -> {
                    todoRepository.delete(todo);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + todoId));

    }
}
