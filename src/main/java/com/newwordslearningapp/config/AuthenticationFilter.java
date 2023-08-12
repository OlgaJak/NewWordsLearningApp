package com.newwordslearningapp.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.newwordslearningapp.entity.User;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Check if the user is authenticated (e.g., by checking if the session contains the user info)
        User user = session != null ? (User) session.getAttribute("loggedInUser") : null;

        // Check if the user is authenticated and has an active session
        if (user != null && session != null) {

            System.out.println("User authenticated: " + user.getEmail());

            // Allow the request to proceed
            chain.doFilter(request, response);
        } else if (!httpRequest.getRequestURI().endsWith("/login")) {

            // If not authenticated and not on the login page, redirect to login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");

        } else {
            // Allow the request to proceed for login page
            chain.doFilter(request, response);
        }
    }
}

