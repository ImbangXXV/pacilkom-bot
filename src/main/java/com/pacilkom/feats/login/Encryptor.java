package com.pacilkom.feats.login;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.pacilkom.bot.PacilkomBot;
import org.apache.commons.codec.binary.Hex;

public class Encryptor {
    public static String encrypt(String value) {
        try {
            String hash = getKeyHash(PacilkomBot.API_KEY, LoginVerifier.CLIENT_ID);
            IvParameterSpec iv = new IvParameterSpec(hash.substring(0, 16).getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(hash.substring(0, 16).getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Hex.encodeHexString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            String hash = getKeyHash(PacilkomBot.API_KEY, LoginVerifier.CLIENT_ID);
            IvParameterSpec iv = new IvParameterSpec(hash.substring(0, 16).getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(hash.substring(0, 16).getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Hex.decodeHex(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String getKeyHash(String passwordToHash, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
