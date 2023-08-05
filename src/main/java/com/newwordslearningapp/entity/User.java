package com.newwordslearningapp.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name= "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;

    @Column(name= "name")
    private String name;

    @Column(name= "email", unique = true)
    private String email;

    @Column(name= "password", length = 1000)
    @Size(min = 5, message = "Minimum 5 symbols")
    private String password;


    @Transient
    @NotEmpty(message = "The field cannot be empty")
    @Size(min = 5, message = "Minimum 5 symbols")
    private String passwordConfirm;


}
