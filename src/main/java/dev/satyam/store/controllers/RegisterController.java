package dev.satyam.store.controllers;

import dev.satyam.store.models.LoginDto;
import dev.satyam.store.models.Register;
import dev.satyam.store.models.RegisterDto;
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

import java.util.Date;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    RegisterRepository registerRepo;

    @GetMapping({"/", ""})
    public String showRegister(Model model, HttpSession session) {

        RegisterDto registerDto = new RegisterDto();
        model.addAttribute("registerDto", registerDto);
//        String userName = (String) session.getAttribute("userName");
//        model.addAttribute("userSession", userName);

        return "register/index";
    }

    @PostMapping({"/", ""})
    public String doRegister(Model model, @Valid @ModelAttribute RegisterDto registerDto, BindingResult result) {
        if (result.hasErrors()) {
            return "register/index";
        }

        Register register = new Register();

        String hashedPassword = PasswordUtils.hashPassword(registerDto.getPassword());
        Date createdAt = new Date();

        register.setUserName(registerDto.getUserName());
        register.setEmail(registerDto.getEmail());
        register.setPassword(hashedPassword);
        register.setCreatedAt(createdAt);

        registerRepo.save(register);

        return "redirect:/register";
    }
}
