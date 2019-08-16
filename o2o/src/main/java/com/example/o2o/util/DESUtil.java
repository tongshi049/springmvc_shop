package com.example.o2o.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

/**
 * DES algorithm for Encoding and Decoding configuration properties in our project
 * It is a symmetric Security algorithm,
 */
public class DESUtil {
    private static Key key;
    //setting key
    private static String KEY_STR = "mykey";
    private static String CHARSETNAME = "UTF-8";
    private static String ALGORITHM = "DES";

    /**
     * generate key for encoding
     */
    static {
        try {
            // create a generator instance for generating code using DES algorithm
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            // Using SHA1 security strategy
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            // Setting seed
            secureRandom.setSeed(KEY_STR.getBytes());
            generator.init(secureRandom);
            key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * To Encrypt a String
     *
     * @param str:String need to Encrypt
     * @return
     */
    public static String getEncryptString(String str) {
        //BASE64 Encoding, receiving byte[] array and change it to String
        BASE64Encoder base64Decoder = new BASE64Encoder();
        try {
            //Encoding way:UTF-8
            byte[] bytes = str.getBytes(CHARSETNAME);
            // create a cipher based on DES algorithm
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // using generated key to init the cipher
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // get the encrypted bytes
            byte[] doFinal = cipher.doFinal(bytes);
            // return the encrypted String
            return base64Decoder.encode(doFinal);
        } catch (Exception e) {
            //TODO:handle exception
            throw new RuntimeException(e);
        }
    }

    public static String getDecryptString(String str){
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try{
            //Encoding way:UTF-8
            byte[] bytes = base64Decoder.decodeBuffer(str);
            //get he cipher instance
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // using the generated key
            cipher.init(Cipher.DECRYPT_MODE,key);
            // get the decoded byte stream
            byte[] doFinal = cipher.doFinal(bytes);
            // change it to string using UTF_8 charset
            return new String(doFinal,CHARSETNAME);
        }catch (Exception e){
            //TODO:handle exception
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        System.out.println(getEncryptString("user"));
        System.out.println(getEncryptString("password"));
    }
}
