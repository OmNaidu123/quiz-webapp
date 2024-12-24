package com.example.quiz_service.Feign;

import com.example.quiz_service.Models.QuestionWrapper;
import com.example.quiz_service.Models.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> generate(@RequestParam String category, @RequestParam int numQ);

    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> questions(@RequestBody List<Integer> questionIds);

    @PostMapping("question/score")
    public ResponseEntity<Integer> score(@RequestBody List<Response> responses);
}
