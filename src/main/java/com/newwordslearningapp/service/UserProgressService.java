package com.newwordslearningapp.service;

import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.entity.UserProgress;
import com.newwordslearningapp.repository.UserLearnedWordsRepository;
import com.newwordslearningapp.repository.UserProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProgressService {


    private UserProgressRepository userProgressRepository;

    @Autowired
    public UserProgressService(UserProgressRepository userProgressRepository) {
        this.userProgressRepository = userProgressRepository;
    }

    // Method to get user's progress from database
    public List<UserProgress> getUserProgressByUser(User user) {
        return userProgressRepository.findByUser(user);
    }

    // Method for saving user's progress in database
    public void saveUserProgress(UserProgress userProgress) {
        userProgressRepository.save(userProgress);
    }
}