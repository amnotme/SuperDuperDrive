package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
@Mapper
public interface NoteMapper {

    @Select(
        "SELECT * FROM NOTES " +
        "WHERE userid = #{userId}"
    )
    ArrayList<Note> getNotesByUserId(Integer userId);

    @Insert(
        "INSERT INTO NOTES " +
        "(notetitle, notedescription, userid) " +
        "VALUES (#{noteTitle}, #{noteDescription}, #{userId})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Delete(
        "DELETE FROM NOTES " +
        "WHERE noteid = #{noteId}"
    )
    int deleteNote(Integer noteId);

    @Update(
        "UPDATE NOTES " +
        "SET notetitle = #{noteTitle}," +
            "notedescription = #{noteDescription} " +
        "WHERE noteid = #{noteId}"
    )
    int updateNote(Integer noteId, String noteTitle, String noteDescription);

}
