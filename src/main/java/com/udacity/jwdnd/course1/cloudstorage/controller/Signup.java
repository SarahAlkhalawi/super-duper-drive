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
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/signup")
public class Signup {
    private final UserService userservice;

    public Signup(UserService userservice) {
        this.userservice = userservice;
    }

    @GetMapping
    public String signup(){
        return "signup";
    }

    @PostMapping
    public RedirectView signup(@ModelAttribute User user, Errors errors, Model model , RedirectAttributes attributes){
        boolean user1 = userservice.isUserExist(user.getUsername());
        if (!user1){
//            model.addAttribute("errorMessage", "Username already exist!");
//            errors.rejectValue("errorMessage", "Username already exist!");
//            return "redirect:/signup?error="+errors.getAllErrors();
            RedirectView redirectView = new RedirectView("/signup",true);
            attributes.addFlashAttribute("errorMessage","Username already exist!" );
            return redirectView;
//            return "signup";
        }
        else if ((userservice.addUser(user)) < 0){
            model.addAttribute("errorMessage", "Something wrong, try to signup again.");
//            errors.rejectValue("errorMessage", "Username already exist!");
//            return "redirect:/signup?error="+errors.getAllErrors();
            RedirectView redirectView = new RedirectView("/signup",true);
            attributes.addFlashAttribute("errorMessage","Something wrong, try to signup again." );
            return redirectView;
//            return "signup";
        }
        else {
//            model.addAttribute("signedUpSuccessfully", true);
//            model.addAttribute("signupSuccess", true);
//            attributes.addFlashAttribute("signedUpSuccessfully", true);
            RedirectView redirectView = new RedirectView("/login",true);
            attributes.addFlashAttribute("signupSuccess", true);
            return redirectView;
//            return "redirect:/login";
        }
    }
}
