package com.newwordslearningapp.repository;

import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
}
