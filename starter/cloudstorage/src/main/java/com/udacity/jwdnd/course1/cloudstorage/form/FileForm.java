package com.udacity.jwdnd.course1.cloudstorage.form;

import org.springframework.web.multipart.MultipartFile;

public class FileForm {

    private final Integer fileId;

    private final String fileName;

    private final MultipartFile file;

    public FileForm(Integer fileId, String fileName, MultipartFile file) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.file = file;
    }

    public Integer getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public MultipartFile getFile() {
        return file;
    }

}
