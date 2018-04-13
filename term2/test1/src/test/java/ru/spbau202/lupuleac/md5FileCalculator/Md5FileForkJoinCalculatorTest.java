package ru.spbau202.lupuleac.md5FileCalculator;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertArrayEquals;

public class Md5FileForkJoinCalculatorTest {
    @Test
    public void testEqualResults(){
        assertArrayEquals(Md5FileCalculatorFactory.getMdFileSingleThreadedCalculator().calculateHash(new File("aaa")), Md5FileCalculatorFactory.getMdFileForkJoinCalculator().
                calculateHash(new File("aaa/a.txt")));
    }

}