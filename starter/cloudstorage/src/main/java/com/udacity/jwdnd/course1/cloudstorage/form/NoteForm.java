package com.udacity.jwdnd.course1.cloudstorage.form;

public class NoteForm {

    private String noteId;

    private String noteTitle;


    private String noteDescription;

    public NoteForm(
            String noteId,
            String noteTitle,
            String noteDescription
    ) {
        this.noteId = noteId;
        this.noteTitle =  noteTitle;
        this.noteDescription = noteDescription;
    }

    public String getNoteId() {
        return noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

}
