package com.newwordslearningapp.repository;

import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.entity.UserLearnedWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLearnedWordsRepository extends JpaRepository<UserLearnedWords,Long> {

    @Query("SELECT lw FROM UserLearnedWords lw WHERE lw.user = ?1 AND lw.word = ?2 ORDER BY lw.dateOfTask DESC")
    List<UserLearnedWords> findLastLearnedWordsByUserAndWord(User user, String word, int count);
}
