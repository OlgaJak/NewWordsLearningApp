package com.newwordslearningapp.service;

import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.entity.UserLearnedWords;
import com.newwordslearningapp.repository.LearningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class WordExplanationService {

    private LearningRepository learningRepository;

    @Autowired
    public WordExplanationService(LearningRepository learningRepository) {
        this.learningRepository = learningRepository;
    }

    public String getWordForQuiz(Long userId){
        List<UserLearnedWords> words = learningRepository.findTop5ByUserIdAndStatusEqualsOrderByDateOfTaskDesc(userId, true);

        if (!words.isEmpty()) {
            words.add(words.get(0));
        }
        Collections.shuffle(words);
        String quizWord = words.get(0).getWord();

        System.out.println("Here is a new feature");
        System.out.println(quizWord);
        return quizWord;
    }
    public List<String> getFourExplanationsForWord(Long userId) {
        List<UserLearnedWords> lastFiveLearnedWords = learningRepository.findTop5ByUserIdAndStatusEqualsOrderByDateOfTaskDesc(userId, true);
        List<String> explanations = new ArrayList<>();

        // Shuffle the list of last five learned words
        Collections.shuffle(lastFiveLearnedWords);
        // Add the correct explanation
        if (!lastFiveLearnedWords.isEmpty()) {
            explanations.add(lastFiveLearnedWords.get(0).getDefinition());
        }
        // Add three incorrect explanations
        for (int i = 1; i < Math.min(4, lastFiveLearnedWords.size()); i++) {
            explanations.add(lastFiveLearnedWords.get(i).getDefinition());
        }
        return explanations;
    }
}



