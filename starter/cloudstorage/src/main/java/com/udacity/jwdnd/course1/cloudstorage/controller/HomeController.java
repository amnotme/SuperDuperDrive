package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.form.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;

    private final NoteService noteService;
    public HomeController(
        UserService userService,
        NoteService noteService
    ) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping("")
    public String getHome(
        Authentication auth,
        Model model,
        @ModelAttribute("newNote") NoteForm noteForm
    ) {

        model.addAttribute("allNotes",
            this.noteService
                .getNotesByUserId(
                    (this.userService.getUser(auth)).getUserId())
        );

        return "home";
    }

    @GetMapping("/result")
    public String result() {
        return "result";
    }

}
