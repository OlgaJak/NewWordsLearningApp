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

    // Метод для получения прогресса пользователя из базы данных
    public List<UserProgress> getUserProgressByUser(User user) {
        return userProgressRepository.findByUser(user);
    }

    // Метод для сохранения прогресса пользователя в базе данных
    public void saveUserProgress(UserProgress userProgress) {
        userProgressRepository.save(userProgress);
    }
}