package com.newwordslearningapp.controller;

import com.newwordslearningapp.DTO.QuizResult;
import com.newwordslearningapp.DTO.QuizScope;
import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.entity.UserLearnedWords;
import com.newwordslearningapp.entity.UserProgress;
import com.newwordslearningapp.service.UserProgressService;
import com.newwordslearningapp.service.UserStatisticsService;
import com.newwordslearningapp.service.WordExplanationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.*;

@Controller
public class QuizController {
    private final WordExplanationService wordExplanationService;
    private UserProgressService userProgressService;


    @Autowired
    public QuizController(WordExplanationService wordExplanationService, UserProgressService userProgressService) {
        this.wordExplanationService = wordExplanationService;
        this.userProgressService = userProgressService;
    }

    @Autowired
    private UserStatisticsService userStatisticsService;
    private List<QuizScope> quizOptions;

    @GetMapping("/quiz")
    public String quizPage(HttpSession session, Model model) {
        // Retrieve the user from the session
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            // Handle the case when user is not logged in
            return "redirect:/login-form"; // Redirect to login page or handle as needed
        }

        List<UserLearnedWords> fiveWordsForQuiz = this.wordExplanationService.getFiveWordsForQuiz(user.getId());


        ArrayList<QuizScope> quizOptions = new ArrayList<>();

        for (UserLearnedWords userLearnedWords : fiveWordsForQuiz) {
            List<UserLearnedWords> clonedFiveWordsForQuiz = new ArrayList<>(fiveWordsForQuiz);
            Collections.shuffle(clonedFiveWordsForQuiz);
            List<String> result = wordExplanationService.getFourExplanationsForWord(clonedFiveWordsForQuiz, userLearnedWords);
            String correctAnswer = userLearnedWords.getDefinition(); // Get the correct answer
            quizOptions.add(new QuizScope(userLearnedWords.getWord(), result, correctAnswer));
        }

        model.addAttribute("quizOptions", quizOptions);
        session.setAttribute("quizOptions", quizOptions);

        return "quiz";

    }

    @PostMapping("/submitQuiz")
    public String submitQuiz(@RequestParam Map<String, String> answers, HttpSession session, Model model) {
        List<QuizScope> quizOptions = (List<QuizScope>) session.getAttribute("quizOptions");
        // Создаем список для хранения слов, которые пользователь учил во время викторины
        List<String> wordsLearned = new ArrayList<>();
        List<String> definitions = new ArrayList<>(); // Создаем список для определений
        int totalQuestions = quizOptions.size();
        int correctAnswers = 0;
        List<QuizResult> quizResults = new ArrayList<>();
        for (int i = 0; i < quizOptions.size(); i++) {
            QuizScope quizScope = quizOptions.get(i);
            String userAnswer = answers.get("answer_" + i);
            System.out.println("User Answer: " + userAnswer);
            System.out.println("Correct Answer: " + quizScope.getCorrectAnswer());
            boolean isCorrect = userAnswer != null && userAnswer.equals(quizScope.getCorrectAnswer());
            if (isCorrect) {
                correctAnswers++;
                wordsLearned.add(quizScope.getQuizWord()); // Добавляем слово в список
                definitions.add(quizScope.getListOfDefinitions().get(i)); // Добавляем определение в список
            }
            quizResults.add(new QuizResult(quizScope, userAnswer, isCorrect));
        }
        int incorrectAnswers = totalQuestions - correctAnswers;
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("incorrectAnswers", incorrectAnswers);
        model.addAttribute("quizResults", quizResults);
        // Сохранение правильных ответов пользователя в базу данных
        StringBuilder correctAnswersStringBuilder = new StringBuilder();
        for (QuizResult result : quizResults) {
            if (result.isCorrect()) {
                correctAnswersStringBuilder.append(result.getQuizScope().getCorrectAnswer()).append(", ");
            }
        }
        String correctAnswersStr = correctAnswersStringBuilder.toString();
        if (correctAnswersStr.length() > 2) {
            correctAnswersStr = correctAnswersStr.substring(0, correctAnswersStr.length() - 2); // Убираем последнюю запятую и пробел
        }
        User user = (User) session.getAttribute("loggedInUser");
        UserProgress userProgress = new UserProgress();
        userProgress.setDateOfTask(new Timestamp(System.currentTimeMillis()));
        userProgress.setUser(user);
        userProgress.setWordsLearned(correctAnswersStr);
        userProgress.setWordsLearned(String.join(", ", wordsLearned)); // Сохраняем список слов через запятую
        userProgress.setDefinition(String.join(", ", definitions)); // Устанавливаем определение
        // Сохранение в базу данных через сервис
        userProgressService.saveUserProgress(userProgress);
        return "quiz-result"; // Верните имя шаблона для отображения результатов
    }
}

