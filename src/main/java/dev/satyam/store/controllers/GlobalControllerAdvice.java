package dev.satyam.store.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addGlobalControllerAttribute(Model model, HttpSession session) {
        String userSession = (String) session.getAttribute("userName");

        if (userSession != null) {
            model.addAttribute("userSession", userSession);
        }

    }
}
