package com.udacity.jwdnd.course1.cloudstorage.form;

public class CredentialForm {


    private String credentialId;

    private String url;

    private String userName;

    private String key;

    private String password;

    public CredentialForm(
        String credentialId,
        String url,
        String userName,
        String key,
        String password
    ) {
        this.credentialId = credentialId;
        this.url = url;
        this.userName = userName;
        this.key = key;
        this.password = password;
    }
    public String getCredentialId() {
        return credentialId;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getKey() {
        return key;
    }

    public String getPassword() {
        return password;
    }

}
