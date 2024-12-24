package com.example.quiz_service.Dao;


import com.example.quiz_service.Models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz, Integer> {


}
