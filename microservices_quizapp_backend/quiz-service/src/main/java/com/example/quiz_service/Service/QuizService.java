package com.example.quiz_service.Service;

import com.example.quiz_service.Dao.QuizDao;
import com.example.quiz_service.Feign.QuizInterface;
import com.example.quiz_service.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        try {
            ResponseEntity<List<Integer>> extract = quizInterface.generate(category, numQ);
            HttpStatusCode status = extract.getStatusCode();
            if(status.equals(HttpStatus.BAD_REQUEST))
                throw new Exception("bad request");
            List<Integer> questions = extract.getBody();
            if(questions.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Quiz q = new Quiz();
            q.setQuizTitle(title);
            q.setQuestions(questions);
            quizDao.save(q);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(int id) {
        try{
            Optional<Quiz> opQ = quizDao.findById(id);
            Quiz q = opQ.orElse(null);
            if(q == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            List<Integer> questions = q.getQuestions();
            return quizInterface.questions(questions);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Integer> calculateScore(List<Response> response) {
        try {
            return quizInterface.score(response);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<QuizWrapper>> getAllQuizs() {
        List<Quiz> quizs =  quizDao.findAll();
        List<QuizWrapper> quizWrappers = new ArrayList<>();
        for(Quiz q : quizs){
            QuizWrapper quizWrapper = new QuizWrapper();
            quizWrapper.setQuizTitle(q.getQuizTitle());
            quizWrapper.setQuizId(q.getQuizId());
            quizWrappers.add(quizWrapper);
        }
        return new ResponseEntity<>(quizWrappers, HttpStatus.OK);
    }
}