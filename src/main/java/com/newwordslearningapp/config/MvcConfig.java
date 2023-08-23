package com.newwordslearningapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/user-page").setViewName("user-page");
        registry.addViewController("/learning").setViewName("learning");
        registry.addViewController("/quiz").setViewName("quiz");
        registry.addViewController("/quiz-result").setViewName("quiz-result");

    }
}
