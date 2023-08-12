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
    @Column (name = "definition")
    private String definition;
    @Column(name = "phonetic")
    private String phonetic;

    @Column(name="status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_progress_id")
    private UserProgress userProgress;

}
