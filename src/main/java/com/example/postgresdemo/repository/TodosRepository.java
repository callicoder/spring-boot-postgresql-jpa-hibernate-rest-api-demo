package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Todos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodosRepository extends JpaRepository<Todos, Long> {
    List<Todos> findByUsersId(Long usersId);
}
