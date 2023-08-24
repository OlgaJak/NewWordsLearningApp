package com.newwordslearningapp.controller;

import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.entity.UserProgress;
import com.newwordslearningapp.service.UserProgressService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserProgressController {

    private UserProgressService userProgressService;

    @Autowired
    public UserProgressController(UserProgressService userProgressService) {
        this.userProgressService = userProgressService;
    }

    @PostMapping("/progress")
    public String userProgressPage(Model model, HttpSession session) {
        // Получите пользователя из сессии
        User user = (User) session.getAttribute("loggedInUser");

        // Получите данные прогресса пользователя из базы данных
        List<UserProgress> userProgressList = userProgressService.getUserProgressByUser(user);

        // Передайте данные в модель для отображения на странице
        model.addAttribute("userProgressList", userProgressList);

        return "progress";
    }
}
