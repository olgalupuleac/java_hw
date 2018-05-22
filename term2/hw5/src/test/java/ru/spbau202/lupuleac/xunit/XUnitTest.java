package ru.spbau202.lupuleac.xunit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class XUnitTest {
    private XUnit xUnit;
    private PrintStream oldStream;
    private ByteArrayOutputStream outputStream;

    private String results = "Test emptyTest passed in 0 seconds.\n" +
            "Test failingTest failed in 0 seconds.\n" +
            "Reason: this test should fail\n" +
            "Method ignoredTest is ignored.\n" +
            "Reason: should be ignored\n" +
            "Test testWithExpectedException passed in 0 seconds.\n" +
            "Total number of tests: 4\n" +
            "Passed: 2\n" +
            "Failed: 1\n";

   @Before
    public void setOutputStream(){
        oldStream = System.out;
        outputStream = new ByteArrayOutputStream();
        PrintStream newStream = new PrintStream(outputStream);
        System.setOut(newStream);
    }

    @After
    public void resetOutputStream(){
        System.setOut(oldStream);
    }

    @Test
    public void runTests() throws Exception {
        try {
            xUnit = new XUnit("ru.spbau202.lupuleac.xunit.TestClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        xUnit.runTests();
        assertEquals(results, outputStream.toString());
    }

}