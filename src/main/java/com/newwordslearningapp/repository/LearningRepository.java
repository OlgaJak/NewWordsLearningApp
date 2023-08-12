package com.newwordslearningapp.repository;


import ch.qos.logback.core.status.Status;
import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.entity.UserLearnedWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningRepository extends JpaRepository<UserLearnedWords, Boolean> {
    public List<UserLearnedWords> findTop5ByUserIdAndStatusEqualsOrderByDateOfTaskDesc(Long userId, boolean status);
}
