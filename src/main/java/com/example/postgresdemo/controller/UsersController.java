package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Users;
import com.example.postgresdemo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/users/viewAll")
    public List<Users> getAllUser(){
        return usersRepository.findAll();
    }

    @GetMapping("users/view/{usersId}")
    public Users getUsersById(@PathVariable Long usersId){
        return usersRepository.findById(usersId).map(users -> {return users;}).orElse(null);
    }

    @PostMapping("/users/create")
    public Users createQuestion(@Valid @RequestBody Users users) {
        return usersRepository.save(users);
    }

    @PutMapping("/users/update/{usersId}")
    public Users updateUsers(@PathVariable Long usersId, @Valid @RequestBody Users usersReq) {
        return usersRepository.findById(usersId)
                .map(users -> {
                    users.setUsername(usersReq.getUsername());
                    users.setName(usersReq.getName());
                    return usersRepository.save(users);
                }).orElseThrow(() -> new ResourceNotFoundException("Users not found with id " + usersId));
    }

    @DeleteMapping("/users/delete/{usersId}")
    public ResponseEntity<?> deleteUsers(@PathVariable Long usersId) {
        return usersRepository.findById(usersId)
                .map(users -> {
                    usersRepository.delete(users);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Users not found with id " + usersId));
    }

}
