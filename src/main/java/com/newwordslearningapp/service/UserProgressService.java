package com.newwordslearningapp.service;

import com.newwordslearningapp.entity.UserProgress;
import com.newwordslearningapp.repository.UserProgressRepository; // Import your UserProgressRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProgressService {
    private final UserProgressRepository userProgressRepository;

    @Autowired
    public UserProgressService(UserProgressRepository userProgressRepository) {
        this.userProgressRepository = userProgressRepository;
    }

    public UserProgress getUserProgress(Long userId) {
        return userProgressRepository.findByUser_Id(userId);
    }
}

