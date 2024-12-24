package com.example.question_service.Controller;


import com.example.question_service.Models.Question;
import com.example.question_service.Models.QuestionWrapper;
import com.example.question_service.Models.Response;
import com.example.question_service.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService service;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> allQuestions() {
        return service.getAllQuestions();
    }

    @GetMapping("{category}")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable String category){
        return service.getCategoryQuestions(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question q){
        return service.addQuestion(q);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> generate(@RequestParam String category, @RequestParam int numQ){
        return service.pickRandomQuestions(category, numQ);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> questions(@RequestBody List<Integer> questionIds){
        return service.getQuestionMapping(questionIds);
    }

    @PostMapping("score")
    public ResponseEntity<Integer> score(@RequestBody List<Response> responses){
        return service.generateScore(responses);
    }
}
