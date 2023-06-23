package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {
    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public Integer addCredential(Credentials credentials, Integer userid){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedKey);
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);

        return credentialsMapper.addCredential(new Credentials(credentials.getUrl(),credentials.getUsername(),
                encodedKey, encryptedPassword, userid));
    }

    public Integer deletecredential(Integer credentialid){
        return credentialsMapper.deleteCredentials(credentialid);
    }

    public Integer editCredential(Credentials credentials){
        Credentials c = credentialsMapper.getCredential(credentials.getUserid(), credentials.getCredentialid());

        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), c.getKey());
        credentials.setPassword(encryptedPassword);
        credentials.setKey(c.getKey());
        return credentialsMapper.updateCredentials(credentials);
    }

    public String encryptedPassword(String p){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(p, encodedKey);
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
        return encryptedPassword;
    }

    public String decryptedPassword(String p, String k){
        String decryptedPassword = encryptionService.decryptValue(p, k);
        return decryptedPassword;
    }

    public List<Credentials> getAllCredentials(Integer userid){
        return credentialsMapper.getCredentials(userid);
    }

    public Credentials getCredential(Integer userid, Integer credentialid){
        return credentialsMapper.getCredential(userid, credentialid);
    }

}
