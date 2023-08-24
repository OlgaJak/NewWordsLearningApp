package com.newwordslearningapp.service;

import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.entity.UserLearnedWords;
import com.newwordslearningapp.repository.LearningRepository;
import com.newwordslearningapp.repository.UserLearnedWordsRepository;
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

    public UserLearnedWords getWordForQuiz(List<UserLearnedWords> words){
        Collections.shuffle(words);
        return words.get(0);
    }
    public List<String> getFourExplanationsForWord(List<UserLearnedWords> lastFiveLearnedWords, UserLearnedWords quizWord){
        List<String> explanations = new ArrayList<>();

        // Add the correct explanation
        explanations.add(quizWord.getDefinition());

        // we remove the quiz word from the list of words we will use for random definitions
        lastFiveLearnedWords.removeIf(word -> word.getId().equals(quizWord.getId()));

        // Shuffle the list of last five learned words - wee keep this one so that the words sin the loop are ramdon each time
//        Collections.shuffle(lastFiveLearnedWords);

        // Add three incorrect explanations
        for (int i = 0; i < 3; i++) {
            explanations.add(lastFiveLearnedWords.get(i).getDefinition());
        }

        // shuffle again so that the explanations are random and the answer is not always the first
        Collections.shuffle(explanations);

        return explanations;
    }

    public List<UserLearnedWords> getFiveWordsForQuiz(Long userId) {
        return learningRepository.findTop5ByUserIdAndStatusEqualsOrderByDateOfTaskDesc(userId, true);
    }
}




