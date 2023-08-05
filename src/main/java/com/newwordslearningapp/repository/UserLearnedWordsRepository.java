package com.newwordslearningapp.repository;

import com.newwordslearningapp.entity.UserLearnedWords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLearnedWordsRepository extends JpaRepository<UserLearnedWords,Long> {
}
