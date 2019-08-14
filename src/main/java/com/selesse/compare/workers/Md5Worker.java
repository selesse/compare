package com.selesse.compare.workers;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;

public class Md5Worker implements Callable<Md5Result> {
    public static String EMPTY_FILE_SHA = "d41d8cd98f00b204e9800998ecf8427e";
    private final Path file;
    private static ThreadLocal<MessageDigest> md5 = ThreadLocal.withInitial(() -> {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    });

    Md5Worker(Path file) {
        this.file = file;
    }

    @Override
    public Md5Result call() {
        try {
            byte[] fileBytes = Files.readAllBytes(file);
            md5.get().update(fileBytes);
            var sha = new BigInteger(1, md5.get().digest()).toString(16);
            md5.get().reset();
            return new Md5Result(file, sha);
        } catch (IOException e) {
            System.out.println("Error calculating md5 for " + file);
            e.printStackTrace();
            return new Md5Result(file, "");
        }
    }
}
