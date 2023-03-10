package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface CredentialMapper {

    @Select(
        "SELECT * FROM CREDENTIALS " +
        "WHERE userid = #{userId} "
    )
    ArrayList<Credential> getCredentialsByUserId(Integer userId);

    @Insert(
        "INSERT INTO CREDENTIALS " +
        "(url, username, key, password, userid) " +
        "VALUES (#{url}, #{userName}, #{key}, #{password}, #{userId})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Update(
        "UPDATE CREDENTIALS " +
        "SET url = #{url}," +
            "username = #{userName}, " +
            "key = #{key}, " +
            "password = #{password} " +
        "WHERE credentialid = #{credentialId}"
    )
    int updateCredential(
        Integer credentialId,
        String url,
        String userName,
        String key,
        String password
    );

    @Delete(
        "DELETE FROM CREDENTIALS " +
        "WHERE credentialid = #{credentialId}"
    )
    int deleteCredential(Integer credentialId);
}
