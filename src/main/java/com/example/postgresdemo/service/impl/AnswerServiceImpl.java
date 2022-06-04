package com.example.postgresdemo.service.impl;

import com.example.postgresdemo.model.Answer;
import com.example.postgresdemo.repository.AnswerRepository;
import com.example.postgresdemo.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AnswerServiceImpl implements AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    @Override
    public Optional<Answer> findById(int id) {
        return answerRepository.findById(id);
    }

    @Override
    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    @Override
    public Answer saveOrUpdate(Answer answer) {
        if (!answerRepository.existsById(answer.getId())) {
            return answerRepository.save(answer);
        } else {
            Answer answer1 = new Answer();
            answer1.setQuestion(answer.getQuestion());
            answer1.setText(answer.getText());
            answer1.setUpdatedAt(new Date());
            answer1.setCreatedAt(answer.getCreatedAt());
            return answerRepository.save(answer1);
        }
    }
}
