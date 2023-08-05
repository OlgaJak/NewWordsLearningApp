package com.newwordslearningapp.repository;

import com.newwordslearningapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProgressRepository extends JpaRepository<User, Long> {
}
