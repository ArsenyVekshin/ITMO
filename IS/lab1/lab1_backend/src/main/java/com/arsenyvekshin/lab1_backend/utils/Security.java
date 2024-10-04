package com.arsenyvekshin.lab1_backend.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
    public static String sha256(String input) {
        try {
            return bytesToHex(MessageDigest.getInstance("SHA-256").digest(input.getBytes()));
        } catch (NoSuchAlgorithmException ignored) { }
        return "";
    }

    public static String getRandomHash() {
        try {
            byte[] randomBytes = new byte[32];
            new java.util.Random().nextBytes(randomBytes);
            return bytesToHex(MessageDigest.getInstance("SHA-256").digest(randomBytes));
        } catch (NoSuchAlgorithmException ignored) { }
        return "";
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
