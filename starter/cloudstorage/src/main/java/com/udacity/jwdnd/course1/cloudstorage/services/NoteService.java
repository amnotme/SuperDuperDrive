package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.form.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService {

    private final NoteMapper noteMapper;


    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public ArrayList<NoteForm> getNotesByUserId(Integer userId) {
        ArrayList<Note> notes = noteMapper.getNotesByUserId(userId);
        ArrayList<NoteForm> noteForms = new ArrayList<>();

        for (Note note: notes) {
            noteForms.add(
                new NoteForm(
                    String.valueOf(note.getNoteId()),
                    note.getNoteTitle(),
                    note.getNoteDescription()
                )
            );
        }

        return noteForms;
    }

    public boolean insertNote(NoteForm noteForm, User user) {
        return (
            this.noteMapper
            .insertNote(
                new Note(
                    0,
                    noteForm.getNoteTitle(),
                    noteForm.getNoteDescription(),
                    user.getUserId()
                )
            ) > 0
        );
    }

    public boolean updateNote(NoteForm noteForm) {
        return (
            this.noteMapper
            .updateNote(
                Integer.valueOf(noteForm.getNoteId()),
                noteForm.getNoteTitle(),
                noteForm.getNoteDescription()
            ) > 0
        );
    }

    public boolean deleteNote(Integer noteId) {
        return (
            this.noteMapper
            .deleteNote(
                noteId
            ) > 0
        );
    }
}
