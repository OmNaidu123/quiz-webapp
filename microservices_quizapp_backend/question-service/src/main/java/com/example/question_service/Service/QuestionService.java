package com.example.question_service.Service;


import com.example.question_service.Dao.QuestionDao;
import com.example.question_service.Models.Question;
import com.example.question_service.Models.QuestionWrapper;
import com.example.question_service.Models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao repo;

    public ResponseEntity<List<Question>> getAllQuestions(){
        try {
            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        }
        catch (Exception e){
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Question>> getCategoryQuestions(String category) {
        try {
            return new ResponseEntity<>(repo.findByCategory(category), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addQuestion(Question q) {
        try {
            repo.save(q);
            return new ResponseEntity<>("success",HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Integer>> pickRandomQuestions(String category, int numQ) {
        try {
            return new ResponseEntity<>(repo.findRandomQuestionsByCategory(category, numQ), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionMapping(List<Integer> questionIds) {
        try {
            List<QuestionWrapper> wrappers = new ArrayList<>();
            int pointer = 0;
            for (int i : questionIds) {
                Question q = repo.findById(i).orElse(null);
                if (q == null)
                    continue;
                wrappers.add(new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4()));
            }
            if (wrappers.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(wrappers, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Integer> generateScore(List<Response> responses) {
        try {
            int score = 0;
            for (Response r : responses) {
                Question q = repo.findById(r.getId()).orElse(null);
                if(q==null)
                    continue;
                if (r.getResponse().equals(q.getRightAnswer()))
                    score++;
            }
            return new ResponseEntity<>(score, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}