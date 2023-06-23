package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userservice;

    public LoginController(UserService userservice) {
        this.userservice = userservice;
    }

    @GetMapping
    public String login(){
        return "login";
    }

    @PostMapping
    public String login(@ModelAttribute User user, Errors errors, Model model, RedirectAttributes attributes){
        boolean user1 = userservice.isUserExist(user.getUsername());
        if (user1){
//            attributes.addFlashAttribute("loginError", true);
            model.addAttribute("loginError",true);
//            errors.rejectValue("loginError", true);
//            return "redirect:/login?error="+errors.getAllErrors();
            return "login";
        }
        else return "redirect:/home";
    }
}
