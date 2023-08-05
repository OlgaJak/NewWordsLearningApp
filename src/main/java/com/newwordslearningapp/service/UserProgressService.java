package com.newwordslearningapp.service;

import com.newwordslearningapp.repository.UserLearnedWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProgressService {
    private UserLearnedWordsRepository userLearnedWordsRepository;
@Autowired
    public UserProgressService(UserLearnedWordsRepository userLearnedWordsRepository) {
        this.userLearnedWordsRepository = userLearnedWordsRepository;
    }
}
