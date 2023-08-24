package com.newwordslearningapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResult {
    private QuizScope quizScope;
    private String userAnswer;
    private boolean isCorrect;

}