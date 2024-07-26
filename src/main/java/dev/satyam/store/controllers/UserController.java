package dev.satyam.store.controllers;

import dev.satyam.store.models.Product;
import dev.satyam.store.models.ProductDto;
import dev.satyam.store.models.User;
import dev.satyam.store.models.UserDto;
import dev.satyam.store.services.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping({"", "/"})
    public String getAllUsers(Model model) {
        List<User> users = userRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("users", users);
        return "users/index";
    }

    @GetMapping("/add")
    public String showAddUser(Model model) {

        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);

        return "users/createUser";
    }

    @PostMapping("/add")
    public String doAddUser(@Valid @ModelAttribute UserDto userDto, BindingResult result) {

        if (result.hasErrors()) {
            return "users/createUser";
        }
        User user = new User();
        user.setFirst_name(userDto.getFirst_name());
        user.setLast_name(userDto.getLast_name());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setCountry(userDto.getCountry());
        user.setBio(userDto.getBio());
        Date createdDate = new Date();
        user.setCreated_at(createdDate);

        userRepo.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String showEditUser(Model model, @RequestParam int id) {
        try {
            User user = userRepo.findById(id).get();
            model.addAttribute("user", user);

            UserDto userDto = new UserDto();

            userDto.setFirst_name(user.getFirst_name());
            userDto.setLast_name(user.getLast_name());
            userDto.setEmail(user.getEmail());
            userDto.setGender(user.getGender());
            userDto.setCountry(user.getCountry());
            userDto.setBio(user.getBio());

            model.addAttribute("userDto", userDto);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            return "redirect:/users";
        }
        return "users/editUser";
    }

    @PostMapping("/edit")
    public String doEditUser(Model model, @RequestParam int id, @Valid @ModelAttribute UserDto userDto, BindingResult result) {
        try {
            User user = userRepo.findById(id).get();
            model.addAttribute("user", user);

            if (result.hasErrors()) {
                return "users/editUser";
            }

            user.setFirst_name(userDto.getFirst_name());
            user.setLast_name(userDto.getLast_name());
            user.setEmail(userDto.getEmail());
            user.setGender(userDto.getGender());
            user.setCountry(userDto.getCountry());
            user.setBio(userDto.getBio());

            userRepo.save(user);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam int id) {
        try {
            User user = userRepo.findById(id).get();
            userRepo.delete(user);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return "redirect:/users";
    }
}
