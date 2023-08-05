package com.newwordslearningapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
@Entity
@Data
@Table(name = "user_learned_words")
public class UserLearnedWords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "date_of_task")
    private Date dateOfTask;
    @Column(name = "word")
    private String word;
    @Column (name = "explanation")
    private String explanation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
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
//    public String getWord() {
//        return word;
//    }
//
//    public void setWord(String word) {
//        this.word = word;
//    }
//
//    public String getExplanation() {
//        return explanation;
//    }
//
//    public void setExplanation(String explanation) {
//        this.explanation = explanation;
//    }
}
