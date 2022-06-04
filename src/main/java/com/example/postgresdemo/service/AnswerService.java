package com.example.postgresdemo.service;

import com.example.postgresdemo.model.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerService {

    Optional<Answer> findById(int id);

    List<Answer> findAll();

    Answer saveOrUpdate(Answer answer);

}
