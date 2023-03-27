package com.proj.togedutch.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

// 암호화 관련 AES128(고급 암호화 표준)
//참고 https://sunghs.tistory.com/119
public class AES128 {
    private String ips;
    private Key keySpec;

    public AES128(String key) {
        try {
            byte[] keyBytes = new byte[16];
            byte[] b = key.getBytes("UTF-8");
            System.arraycopy(b, 0, keyBytes, 0, keyBytes.length);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            this.ips = key.substring(0, 16);
            this.keySpec = keySpec;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String str) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec,
                    new IvParameterSpec(ips.getBytes()));

            byte[] encrypted = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
            String Str = new String(Base64.encodeBase64(encrypted));

            return Str;
        } catch (Exception e) {
            return null;
        }
    }

    public String decrypt(String str) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec,
                    new IvParameterSpec(ips.getBytes(StandardCharsets.UTF_8)));

            byte[] byteStr = Base64.decodeBase64(str.getBytes());
            String Str = new String(cipher.doFinal(byteStr), StandardCharsets.UTF_8);

            return Str;
        } catch (Exception e) {
            return null;
        }
    }
}

