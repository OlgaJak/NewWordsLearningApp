package com.newwordslearningapp.controller;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        userController = new UserController();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowUserPage_UserDisplayNameExists() {
        // Given
        when(session.getAttribute("userDisplayName")).thenReturn("Bill");

        // When
        String viewName = userController.showUserPage(model, session);

        // Then
        verify(model).addAttribute("displayName", "Bill");
        assertEquals("user-page", viewName);
    }

//    @Test
//    public void testShowUserPage_UserDisplayNameNull() {
//        // Given
//        when(session.getAttribute("userDisplayName")).thenReturn(null);
//
//        // When
//        String viewName = userController.showUserPage(model, session);
//
//        // Then
//        verify(model, never()).addAttribute("displayName", any());
//        assertEquals("user-page", viewName);
//    }
}
