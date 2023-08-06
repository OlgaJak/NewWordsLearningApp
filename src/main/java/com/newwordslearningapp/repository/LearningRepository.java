package com.newwordslearningapp.repository;

import com.newwordslearningapp.entity.UserLearnedWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningRepository extends JpaRepository<UserLearnedWords, Boolean> {
}
