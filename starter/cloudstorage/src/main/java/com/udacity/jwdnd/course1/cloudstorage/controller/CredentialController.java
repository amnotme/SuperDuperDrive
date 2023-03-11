package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.form.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

@RequestMapping("/credential")
public class CredentialController {

    private final UserService userService;

    private final CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/add")
    public String insertCredential(
        Authentication auth,
        @ModelAttribute("newCredential") CredentialForm credForm,
        Model model
    ) {
        boolean isResultSaved = false;

        if (!credForm.getCredentialId().isEmpty())
            isResultSaved = this.credentialService.updateCredential(credForm);
        else
            isResultSaved = this.credentialService.insertCredential(credForm, this.userService.getUser(auth));

        if (isResultSaved)
            model.addAttribute("success", true);
        else
            model.addAttribute(
            "errorMessage",
            "Unable to save credential with url: [" + credForm.getUrl() + "]"
        );

        return "result";
    }

    @GetMapping("/delete/{credentialId}")
    public String delete(
        @ModelAttribute("newCredential") CredentialForm credForm,
        @PathVariable("credentialId") String credentialId,
        Model model
    ) {
        model.addAttribute("success", false);

        if (credentialService.deleteCredential(Integer.parseInt(credentialId)))
            model.addAttribute("success", true);
        else
            model.addAttribute(
            "errorMessage",
            "We were unable delete credential with url [" + credForm.getUrl() + "]"
        );

        return "result";
    }
}
