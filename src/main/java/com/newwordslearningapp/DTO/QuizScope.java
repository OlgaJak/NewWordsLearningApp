package com.newwordslearningapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizScope {
    private String quizWord;
    private List<String>listOfDefinitions;
    private String correctAnswer;


}
