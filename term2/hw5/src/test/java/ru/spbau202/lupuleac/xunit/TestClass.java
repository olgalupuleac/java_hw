package ru.spbau202.lupuleac.xunit;

import ru.spbau202.lupuleac.annotations.*;

public class TestClass {
    @Test
    public void emptyTest(){
    }

    @Test
    public void failingTest(){
        throw new RuntimeException("this test should fail");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testWithExpectedException(){
        int[] a = new int[10];
        a[100] = 6;
    }

    @Test(ignore = "should be ignored")
    public void ignoredTest(){
    }
}