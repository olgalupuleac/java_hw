package ru.spbau202.lupuleac.md5FileCalculator;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertArrayEquals;

public class Md5FileSingleThreadedCalculatorTest {

    @Test
    public void testSimple(){
        File file = new File("aaa/bbb/b.txt");
        Md5FileSingleThreadedCalculator calculator = new Md5FileSingleThreadedCalculator();
        MessageDigest expected = null;
        try(InputStream inputStream = new FileInputStream(file)){
            expected = MessageDigest.getInstance("MD5");
            expected.update(IOUtils.toByteArray(inputStream));


        } catch (Exception e) {
            e.printStackTrace();
        }
        assertArrayEquals(expected.digest(), calculator.calculateHash(file));
    }
}