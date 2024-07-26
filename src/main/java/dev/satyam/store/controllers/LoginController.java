package dev.satyam.store.controllers;

import dev.satyam.store.models.LoginDto;
import dev.satyam.store.models.Register;
import dev.satyam.store.services.RegisterRepository;
import dev.satyam.store.utils.PasswordUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    RegisterRepository registerRepo;

    @GetMapping({"/", ""})
    public String showLogin(Model model) {
        LoginDto loginDto = new LoginDto();
        model.addAttribute("loginDto", loginDto);

        return "login/index";
    }

    @PostMapping({"/", ""})
    public String doLogin(Model model, @Valid @ModelAttribute LoginDto loginDto, BindingResult result, HttpSession session) {

        if (result.hasErrors()) {
            return "login/index";
        }

        Optional<Register> userLoginOpt = registerRepo.findByEmail(loginDto.getEmail());
        if (userLoginOpt.isPresent()) {
            Register userLogin = userLoginOpt.get();
            boolean validPassword = PasswordUtils.verifyPassword(loginDto.getPassword(), userLogin.getPassword());

            if (validPassword) {
                session.setAttribute("userName", userLogin.getUserName());
                return "redirect:/register";
            } else {
                model.addAttribute("loginError", "Email/Password was incorrect!");
            }

        } else {
            System.out.println("No User Found with Email");
            model.addAttribute("loginError", "Email/Password was incorrect!");
        }

        return "login/index";
    }
}
