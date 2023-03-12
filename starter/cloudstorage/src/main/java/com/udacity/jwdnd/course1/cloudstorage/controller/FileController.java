package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.form.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@ControllerAdvice
@RequestMapping("/file")
public class FileController {

    private final UserService userService;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFilUploadError(Model model) {
        System.out.println(("Caught Error"));
        model.addAttribute("error", true);
        model.addAttribute("errorMessage", "your file could not be uploaded because it's too big.");
        return "result";

    }
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/add")
    public String addFile(
        Authentication auth,
        @ModelAttribute("newFile") FileForm fileForm,
        @RequestParam("fileUpload") MultipartFile multipartFile,
        Model model
    ) {

        try {
            if (this.isFileNameTakenOrNoFileUploaded(multipartFile.getOriginalFilename())) {
                model.addAttribute("error", true);

                if (multipartFile.getOriginalFilename().isEmpty())
                    model.addAttribute(
                    "errorMessage",
                    "Please 'choose a file' before attempting to upload. "
                    );
                else
                    model.addAttribute("errorMessage", "Seems like filename: " +
                        multipartFile.getOriginalFilename() +
                        " is already taken. Try a different name. "
                    );
            } else {
                this.fileService.insertFile(multipartFile, this.userService.getUser(auth));
                model.addAttribute("success", true);
            }
        } catch (IOException | RuntimeException exception) {
            String errorMessage = exception.getLocalizedMessage();
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Seems like an error occurred: " +
                errorMessage
            );
        }
        return "result";
    }

    @GetMapping(
        value = "/get/{fileName}",
        produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody byte[] findFile(
        @PathVariable("fileName") String fileName
    ) {
        return fileService.getFile(fileName).getFileData();
    }

    @GetMapping("/delete/{fileName}")
    public String deleteFile(
        @ModelAttribute("newFile") FileForm fileForm,
        @PathVariable("fileName") String fileName,
        Model model
    ) {
        model.addAttribute("success", false);

        if (this.fileService.deleteFile(fileName))
            model.addAttribute("success", true);
        else
            model.addAttribute(
            "errorMessage",
            "We were unable delete file: [" + fileName + "]"
        );

        return "result";
    }


    private boolean isFileNameTakenOrNoFileUploaded(String fileName) {
        if (fileName.isEmpty())
            return true;

        File file = this.fileService.getFile(fileName);
        return file != null;
    }
}
