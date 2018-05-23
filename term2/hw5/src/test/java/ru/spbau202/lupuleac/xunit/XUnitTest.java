package ru.spbau202.lupuleac.xunit;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class XUnitTest {
    private XUnit xUnit;
    static int beforeClass;
    static int afterClass;
    static int after;
    static int before;

    @Test
    public void runTests() throws Exception {
        try {
            xUnit = new XUnit("ru.spbau202.lupuleac.xunit.TestClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        xUnit.runTests();
        HashMap<String, XUnit.TestResult> results = xUnit.getTestResults();
        assertEquals(4, results.size());
        assertEquals(XUnit.TestStatus.PASSED, results.get("emptyTest").getResult());
        assertEquals(XUnit.TestStatus.IGNORED, results.get("ignoredTest").getResult());
        assertEquals("should be ignored", results.get("ignoredTest").getReason());
        assertEquals(XUnit.TestStatus.FAILED, results.get("failingTest").getResult());
        assertEquals("this test should fail", results.get("failingTest").getReason());
        assertEquals(XUnit.TestStatus.PASSED, results.get("testWithExpectedException").getResult());

        assertEquals(1, afterClass);
        assertEquals(1, beforeClass);
        assertEquals(3, before);
        assertEquals(3, after);
    }
}