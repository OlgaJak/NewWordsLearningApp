package com.newwordslearningapp.service;

import com.newwordslearningapp.entity.UserLearnedWords;
import com.newwordslearningapp.repository.UserLearnedWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLearnedWordsService {
    private UserLearnedWordsRepository userLearnedWordsRepository;

    @Autowired
    public UserLearnedWordsService(UserLearnedWordsRepository userLearnedWordsRepository) {
        this.userLearnedWordsRepository = userLearnedWordsRepository;
    }

    public UserLearnedWords saveLearnedWord(UserLearnedWords learnedWord) {

        return userLearnedWordsRepository.save(learnedWord);
    }
}



