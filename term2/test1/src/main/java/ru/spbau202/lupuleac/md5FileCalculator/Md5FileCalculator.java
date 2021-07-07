package ru.spbau202.lupuleac.md5FileCalculator;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

/**
 * Abstract class for calculating directory check-sum.
 */
public abstract class Md5FileCalculator {
    protected static final int BUFFER_SIZE = 1024;

    public abstract byte[] calculateHash(@NotNull File file);

    /**
     * Calculates the hash of one file.
     * @param file is a file which hash is to be calculated.
     * @param messageDigest
     * @return
     */
    @NotNull
    protected static byte[] hashFromFile(@NotNull File file, MessageDigest messageDigest) {
        DigestInputStream digestInputStream = null;
        try (InputStream inputStream = new FileInputStream(file)) {
            digestInputStream = new DigestInputStream(inputStream, messageDigest);
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((digestInputStream.read(buffer) != -1)) {
            }

        } catch (IOException ignored) {
        }
        return digestInputStream.getMessageDigest().digest();
    }
}
