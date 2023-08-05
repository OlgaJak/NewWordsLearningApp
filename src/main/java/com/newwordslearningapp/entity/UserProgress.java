package com.newwordslearningapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity(name = "user_progress")
@Table
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "date_of_task")
    private Date dateOfTask;
    @Column(name = "words_learned")
    private String wordsLearned;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public UserProgress(int id, Date dateOfTask, String wordsLearned) {
        this.id = id;
        this.dateOfTask = dateOfTask;
        this.wordsLearned = wordsLearned;
    }

    public UserProgress() {

    }

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public Date getDateOfTask() {
//        return dateOfTask;
//    }
//
//    public void setDateOfTask(Date dateOfTask) {
//        this.dateOfTask = dateOfTask;
//    }
//
//    public String getWordsLearned() {
//        return wordsLearned;
//    }
//
//    public void setWordsLearned(String wordsLearned) {
//        this.wordsLearned = wordsLearned;
//    }
}