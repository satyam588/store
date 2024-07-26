package dev.satyam.store.controllers;

import dev.satyam.store.models.Todo;
import dev.satyam.store.models.TodoDto;
import dev.satyam.store.services.TodoRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    TodoRepository todoRepo;

    @GetMapping({"", "/"})
    public String toDos(Model model) {
        List<Todo> todos = todoRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("todos", todos);

        return "todo/index";
    }

    @GetMapping("/sort")
    public String sortTodos(Model model, @RequestParam String key) {
        List<Todo> todos = new ArrayList<>();

        switch (key) {
            case "a-z":
                todos = todoRepo.findAll(Sort.by(Sort.Direction.ASC, "title"));
                break;
            case "z-a":
                todos = todoRepo.findAll(Sort.by(Sort.Direction.DESC, "title"));
                break;
            case "new":
                todos = todoRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
                break;
            case "old":
                todos = todoRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
                break;
            default:
                todos = todoRepo.findAll();
        }

        model.addAttribute("todos", todos);

        return "todo/index";
    }

    @GetMapping("/details")
    public String getSingleTodo(@RequestParam int id, Model model) {
        try {
            Todo todo = todoRepo.findById(id).get();
            model.addAttribute("todo", todo);

            return "todo/singleTodo";
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return "redirect:/todos";
    }

    @GetMapping("/add")
    public String showAddTodo(Model model) {

        TodoDto todoDto = new TodoDto();
        model.addAttribute("todoDto", todoDto);

        return "todo/addTodo";
    }

    @PostMapping("/add")
    public String doAddTodo(@Valid @ModelAttribute TodoDto todoDto, BindingResult result) {
        if (result.hasErrors()) {
            return "todo/addTodo";
        }

        Date createdDate = new Date();
        LocalDateTime now = LocalDateTime.now();

        Todo todo = new Todo();
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCreated_at(createdDate);
        todo.setDue_date(todoDto.getDue_date());
        todo.setUpdated_at(now);

        todoRepo.save(todo);

        return "redirect:/todos";
    }

    @GetMapping("/edit")
    public String showEditTodo(@RequestParam int id, Model model) {
        try {
            Todo todo = todoRepo.findById(id).get();
            model.addAttribute("todo", todo);

            TodoDto todoDto = new TodoDto();
            todoDto.setTitle(todo.getTitle());
            todoDto.setDescription(todo.getDescription());
            todoDto.setDue_date(todo.getDue_date());

            model.addAttribute("todoDto", todoDto);

            return "todo/editTodo";
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
        return "redirect:/todos";
    }

    @PostMapping("/edit")
    public String doEditTodo(Model model, @RequestParam int id, @Valid @ModelAttribute TodoDto todoDto, BindingResult result) {
        try {
            Todo todo = todoRepo.findById(id).get();
            model.addAttribute("todo", todo);

            if (result.hasErrors()) {
                return "todo/editTodo";
            }

            todo.setTitle(todoDto.getTitle());
            todo.setDescription(todoDto.getDescription());
            todo.setDue_date(todoDto.getDue_date());

            todoRepo.save(todo);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return "redirect:/todos";
    }

    @GetMapping("/delete")
    public String deleteTodo(@RequestParam int id) {
        try {
            Todo todo = todoRepo.findById(id).get();
            todoRepo.delete(todo);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return "redirect:/todos";
    }

}
