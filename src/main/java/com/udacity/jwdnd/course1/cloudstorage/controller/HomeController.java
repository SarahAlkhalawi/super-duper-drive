package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userservice;
    @Autowired
    private FileService fileService;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private EncryptionService encryptionService;

    public HomeController(NoteService noteService, UserMapper userMapper, UserService userservice,
                          FileService fileService, CredentialsService credentialsService,
                          EncryptionService encryptionService) {
        this.noteService = noteService;
        this.userMapper = userMapper;
        this.userservice = userservice;
        this.fileService = fileService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
    }


    @GetMapping("/result")
    public String result(){
        return "result";
    }

    @GetMapping("/home")
    public String home( Model model, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        List<Note> noteList = noteService.getNotes(user.getUserId());
        model.addAttribute("allNotes", noteList);

        List<File> fileList = fileService.getAllFiles(user.getUserId());
        model.addAttribute("allFiles", fileList);

        model.addAttribute("encryptionService",encryptionService);
        List<Credentials> credentialsList = credentialsService.getAllCredentials(user.getUserId());
        model.addAttribute("allCredentials", credentialsList);
        return "home";
    }


}
