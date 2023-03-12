package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface FileMapper {

    @Select(
        "SELECT * FROM FILES " +
        "WHERE filename = #{fileName} "
    )
    File getFile(String fileName);

    @Select(
        "SELECT * FROM FILES " +
        "WHERE userid = #{userId}"
    )
    ArrayList<File> getFilesByUserId(Integer userId);


    @Insert(
        "INSERT INTO FILES " +
        "(filename, contenttype, filesize, userid, filedata) " +
        "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);


    @Delete(
        "DELETE FROM FILES " +
        "WHERE filename = #{fileName}"
    )
    int deleteFile(String fileName);
}
