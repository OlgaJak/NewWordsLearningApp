package com.newwordslearningapp.service;

import com.newwordslearningapp.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserStatisticsService {

    private final Map<User, Integer> userWordCountMap = new HashMap<>();

    public void initializeUserProgress(User user) {
        userWordCountMap.put(user, 0);
    }

    public int getUserWordCount(User user) {
        return userWordCountMap.getOrDefault(user, 0);
    }

    public void incrementUserWordCount(User user) {
        userWordCountMap.put(user, getUserWordCount(user) + 1);
    }

    public void resetUserWordCount(User user) {
        userWordCountMap.put(user, 0);
    }

    }

