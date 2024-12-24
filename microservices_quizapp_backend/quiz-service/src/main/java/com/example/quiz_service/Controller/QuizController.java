package com.example.quiz_service.Controller;

import com.example.quiz_service.Models.QuestionWrapper;
import com.example.quiz_service.Models.QuizWrapper;
import com.example.quiz_service.Models.Response;
import com.example.quiz_service.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        return quizService.createQuiz(category,numQ,title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable int id){
        return quizService.getQuiz(id);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<QuizWrapper>> getAllQuiz(){
        return quizService.getAllQuizs();
    }

    @PostMapping("submit")
    public ResponseEntity<Integer> submit(@RequestBody List<Response> response){
        return quizService.calculateScore(response);
    }
}
