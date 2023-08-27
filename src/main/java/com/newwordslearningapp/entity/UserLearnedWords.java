package com.newwordslearningapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_learned_words")
public class UserLearnedWords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_of_task", columnDefinition = "TIMESTAMP(0)")
    private Timestamp dateOfTask;

    @Column(name = "word")
    private String word;
    @Column(name = "definition", columnDefinition = "TEXT")
    //@Column (name = "definition")
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
