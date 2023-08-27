package com.newwordslearningapp.service;

import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.repository.UserRepository;
import com.newwordslearningapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void testRegisterUser_Success() {
        // Given
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail("john@example.com")).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);

        // When
        User registeredUser = userService.registerUser(user);

        // Then
        assertNotNull(registeredUser);
        assertEquals("John Doe", registeredUser.getName());
    }

    @Test
    public void testRegisterUser_DuplicateEmail() {
        // Given
        User user = new User();
        user.setEmail("john@example.com");

        when(userRepository.findByEmail("john@example.com")).thenReturn(user);

        // When / Then
        assertThrows(RuntimeException.class, () -> userService.registerUser(user));
    }

    @Test
    public void testFindByEmail() {
        // Given
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");

        when(userRepository.findByEmail("john@example.com")).thenReturn(user);

        // When
        User foundUser = userService.findByEmail("john@example.com");

        // Then
        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
    }
}
