package com.newwordslearningapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user_progress")
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "userProgress", cascade = CascadeType.ALL)
    private List<UserLearnedWords> userLearnedWordsList = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "user_test_words",
            joinColumns = @JoinColumn(name = "user_progress_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id")
    )
    private List<UserLearnedWords> correctTestWordsList = new ArrayList<>();



}