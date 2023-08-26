package com.newwordslearningapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
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

    @Column(name = "date_of_task", columnDefinition = "TIMESTAMP(0)")
    private Timestamp dateOfTask;

    @Column(name = "words_learned")
    private String wordsLearned;

    //@Column(name = "definition")
    @Column(name = "definition", columnDefinition = "LONGTEXT")
    private String definition;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "userProgress", cascade = CascadeType.ALL)
    private List<UserLearnedWords> userLearnedWordsList;
}