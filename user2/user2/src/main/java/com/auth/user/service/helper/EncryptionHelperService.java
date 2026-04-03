package com.auth.user.service.helper;

import com.auth.user.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
@Component
public class EncryptionHelperService {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    @Value("${encryption.secret.key}")
    private String key;

    public EncryptionHelperService(){
    }

    public String encrypt(String value){
        try{
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, spec);
            byte[] encryptedBytes = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }catch (Exception e){
            log.warn("[EncryptionService] : encrypt : failed to encrypt with error {}" , e.getMessage());
            throw new BusinessException("Error occurred while saving password, please try again." , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String decrypt(String value){
        try{
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, spec);
            byte[] decodedBytes = Base64.getDecoder().decode(value);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        }catch (Exception e){
            log.warn("[EncryptionService] : decrypt : failed to decrypt with error {}" , e.getMessage());
            throw new BusinessException("Error occurred while fetching password, please try again" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
