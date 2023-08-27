package com.newwordslearningapp.entity;

import com.newwordslearningapp.entity.User;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUser() {
        User user = new User(1L, "John Doe", "john@example.com", "password123");
        var violations = validator.validate(user);
        assertEquals(0, violations.size(), "There should be no validation violations");
    }

    @Test
    void testInvalidEmail() {
        User user = new User(2L, "Jane Smith", "invalid_email", "password456");
        var violations = validator.validate(user);
        assertEquals(0, violations.size(), "There should be one validation violation");
    }

    @Test
    void testWeakPassword() {
        User user = new User(3L, "Bob Johnson", "bob@example.com", "123");
        var violations = validator.validate(user);
        assertEquals(1, violations.size(), "There should be one validation violation");
    }
}
