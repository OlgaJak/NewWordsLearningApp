package com.newwordslearningapp.service;

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
}
