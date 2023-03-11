package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.form.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/note")
public class NoteController {


    private final UserService userService;

    private final NoteService noteService;

    public NoteController(
            NoteService noteService,
            UserService userService
    ) {
        this.noteService = noteService;
        this.userService = userService;
    }


    @PostMapping("/add")
    public String insertNote(
        Authentication auth,
        @ModelAttribute("newNote") NoteForm noteForm,
        Model model
    ) {
        boolean isResultSaved = false;

        if (!noteForm.getNoteId().isEmpty())
            isResultSaved = this.noteService.updateNote(noteForm);
        else
            isResultSaved = this.noteService.insertNote(noteForm, this.userService.getUser(auth));

        model.addAttribute("success", false);

        if (isResultSaved)
            model.addAttribute("success", true);
        else
            model.addAttribute(
            "errorMessage",
            "Unable to save note with title: [" + noteForm.getNoteTitle() + "]"
        );


        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String delete(
        @ModelAttribute("newNote") NoteForm noteForm,
        @PathVariable("noteId") String noteId,
        Model model
    ) {

        model.addAttribute("success", false);

        if (noteService.deleteNote(Integer.parseInt(noteId)))
            model.addAttribute("success", true);
        else
            model.addAttribute(
            "errorMessage",
            "We were unable delete note with noteId [" + noteForm.getNoteId() + "]"
        );

        return "result";
    }
}
