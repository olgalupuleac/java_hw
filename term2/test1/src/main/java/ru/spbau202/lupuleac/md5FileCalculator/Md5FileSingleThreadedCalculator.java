package ru.spbau202.lupuleac.md5FileCalculator;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class Md5FileSingleThreadedCalculator extends Md5FileCalculator {
    @Override
    @NotNull
    public byte[] calculateHash(@NotNull File file) {
        MessageDigest messageDigest = null;
        try{
            messageDigest = MessageDigest.getInstance("MD5");
            if(file.isDirectory()){
                messageDigest.update(file.getName().getBytes());
                for(File subFile : file.listFiles()){
                    messageDigest.update(calculateHash(subFile));
                }
            }
            else {
                return hashFromFile(file, messageDigest);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return messageDigest.digest();
    }

}
