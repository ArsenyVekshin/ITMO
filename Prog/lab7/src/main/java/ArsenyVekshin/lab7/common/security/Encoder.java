package ArsenyVekshin.lab7.common.security;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Encoder {

    public static byte[] getSHA(String input){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toHexString(byte[] hash){
        BigInteger number = new BigInteger(1, hash);
        return number.toString(16);
    }

    public static String getSHAString(String input){
        return toHexString(getSHA(input));
    }
}

