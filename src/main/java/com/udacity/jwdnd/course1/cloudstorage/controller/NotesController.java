package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NotesController {


    private final NoteService noteService;

    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/notes")
    public String addNoteAndUpdateNote(@ModelAttribute("noteForm") Note noteForm, Authentication authentication, Model model){
        User user = (User) authentication.getPrincipal();
        noteForm.setUserid(user.getUserId());
        if (noteForm.getNoteid() > 0 ){
            Integer success = noteService.updateNote(noteForm);
            System.out.println("Note updated");
            model.addAttribute("success", true);
            return "redirect:/result?success";
        }else {
            noteService.addNote(new Note(noteForm.getNotetitle(),noteForm.getNotedescription(),noteForm.getUserid()),
                    user.getUserId());
            System.out.println("Note added");
            model.addAttribute("success", true);
            return "redirect:/result?success";
        }
    }



    @GetMapping("/notes/delete")
    public String deleteNote(@RequestParam("noteId") Integer noteid, Model model){
        if (noteid > 0){
            noteService.deleteNote(noteid);
            model.addAttribute("success", true);
            return "redirect:/result?success";
        }
        model.addAttribute("error", true);
        model.addAttribute("error", "There is error deleting the note, try again!");
        return "redirect:/result?error";
    }
}
