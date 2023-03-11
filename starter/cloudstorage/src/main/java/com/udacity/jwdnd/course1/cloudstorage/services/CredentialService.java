package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.form.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;

    private final EncryptionService encryptionService;

    public CredentialService(
        CredentialMapper credentialMapper,
        EncryptionService encryptionService
    ) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public ArrayList<CredentialForm> getCredentialsByUserId(Integer userId) {
        ArrayList<Credential> credentials = credentialMapper.getCredentialsByUserId(userId);
        ArrayList<CredentialForm> credentialForms = new ArrayList<>();

        for (Credential cred: credentials) {
            credentialForms.add(
                new CredentialForm(
                    String.valueOf(cred.getCredentialId()),
                    cred.getUrl(),
                    cred.getUserName(),
                    cred.getKey(),
                    cred.getPassword()
                )
            );
        }
        return credentialForms;
    }

    public boolean insertCredential(CredentialForm credForm, User user) {
        String key = this.encryptionService.encodeKey();
        return (
            this.credentialMapper
            .insertCredential(
                new Credential(
            0,
                    credForm.getUrl(),
                    credForm.getUserName(),
                    key,
                    this.encryptionService.encryptPassword(credForm.getPassword(), key),
                    user.getUserId()
                )
            ) > 0
        );
    }

    public boolean updateCredential(CredentialForm credForm) {
        String key = this.encryptionService.encodeKey();
        return (
            this.credentialMapper
            .updateCredential(
                Integer.parseInt(credForm.getCredentialId()),
                credForm.getUrl(),
                credForm.getUserName(),
                key,
                this.encryptionService.encryptPassword(credForm.getPassword(), key)
            ) > 0
        );
    }

    public boolean deleteCredential(Integer credentialId) {
        return (
            this.credentialMapper
            .deleteCredential(
                credentialId
            ) > 0
        );
    }


}
