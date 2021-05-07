package com.yoga.authentication.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.function.Function;


@Slf4j
@UtilityClass
public class AESUtil {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static String encrypt(String data, String requestId, Function<String,String> keyFun) {
        try
        {
            String secretkey = keyFun.apply("aes.secret.key");
            setKey(secretkey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            log.error("{} Error while encrypting: {}",requestId, e.toString());
        }
        return null;
    }

    public static String decrypt(String encryptedData, String requestId, Function<String,String> keyFun) {
        try
        {
            String secretkey = keyFun.apply("aes.secret.key");
            setKey(secretkey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));
        }
        catch (Exception e)
        {
            log.error("{} Error while decrypting: {}", requestId,e.toString());
        }
        return null;
    }

    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}
