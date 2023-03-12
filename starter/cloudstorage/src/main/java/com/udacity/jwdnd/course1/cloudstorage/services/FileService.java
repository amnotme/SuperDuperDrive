package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.form.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class FileService {

    public final UserService userService;

    public final FileMapper fileMapper;

    public FileService(UserService userService, FileMapper fileMapper) {
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    public File getFile(String fileName) {
        return this.fileMapper.getFile(fileName);
    }

    public ArrayList<FileForm> getFilesByUserId(Integer userId) {
        ArrayList<File> files = fileMapper.getFilesByUserId(userId);
        ArrayList<FileForm> fileForms = new ArrayList<>();

        for (File file : files) {
            fileForms.add(
                new FileForm(
                     file.getFileId(),
                     file.getFileName(),
                     null
                )
            );
        }
        return fileForms;
    }

    public void insertFile(MultipartFile multipartFile, User user) throws IOException {
        this.fileMapper.insertFile(
            new File(
            0,
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                String.valueOf(multipartFile.getSize()),
                user.getUserId(),
                multipartFile.getBytes()
            )
        );
    }

    public boolean deleteFile(String fileName) {
        return (
          this.fileMapper.deleteFile(fileName) > 0
        );
    }

}
