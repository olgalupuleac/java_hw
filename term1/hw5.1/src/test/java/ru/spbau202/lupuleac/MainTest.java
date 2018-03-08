package ru.spbau202.lupuleac;

import org.junit.Test;

import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void mainTest() throws Exception {
        final String inputFileName = "src/test/in.txt";
        final String outPutFileName = "src/test/out.txt";
        String[] args = {inputFileName, outPutFileName};
        Main.main(args);
        File out = new File(outPutFileName);
        Scanner scanner = new Scanner(out);
        for (int i = 1; i < 11; i++) {
            if (i % 2 == 0) {
                assertEquals("null", scanner.nextLine());
            } else {
                Integer expected = i * i * 100;
                assertEquals(expected.toString(), scanner.nextLine());
            }
        }
        assertFalse(scanner.hasNextLine());
    }

}