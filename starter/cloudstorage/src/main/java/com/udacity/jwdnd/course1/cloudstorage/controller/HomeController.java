package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.form.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.form.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.form.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
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

    private final CredentialService credentialService;

    private final EncryptionService encryptionService;

    private final FileService fileService;

    public HomeController(
        UserService userService,
        NoteService noteService,
        CredentialService credentialService,
        EncryptionService encryptionService,
        FileService fileService
    ) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
    }

    @GetMapping("")
    public String getHome(
        Authentication auth,
        Model model,
        @ModelAttribute("newNote") NoteForm noteForm,
        @ModelAttribute("newCredential") CredentialForm credForm,
        @ModelAttribute("newFile") FileForm fileForm
    ) {
        model.addAttribute("encryptionService",
                this.encryptionService
        );

        model.addAttribute("allNotes",
            this.noteService
                .getNotesByUserId(
                    (this.userService.getUser(auth)).getUserId())
        );

        model.addAttribute("allCredentials",
            this.credentialService
                .getCredentialsByUserId(
                    (this.userService.getUser(auth)).getUserId())
                );

        model.addAttribute("allFiles",
            this.fileService
                .getFilesByUserId(
                    (this.userService.getUser(auth)).getUserId())
                );

        return "home";
    }

    @GetMapping("/result")
    public String result() {
        return "result";
    }

}
