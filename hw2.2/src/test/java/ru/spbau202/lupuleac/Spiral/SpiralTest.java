package ru.spbau202.lupuleac.Spiral;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

import java.io.*;
import java.util.Random;

/**
 * Tests Spiral class.
 */
public class SpiralTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    /**
     * Sets system output to another stream to test the output of printSpiral method.
     */
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Returns system output back to normal.
     */
    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    /**
     * Tests Spiral.printSpiral method in case of odd length of given array.
     * The array size is small so the expected result could be predicted and compared to actual.
     *
     * @throws Exception
     * @see Spiral#printSpiral(int[][], PrintStream) 
     */
    @Test
    public void printSpiralOdd() throws Exception {
        final int n = 5;
        int[][] array = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = i * n + j;
            }
        }
        Spiral.printSpiral(array, System.out);
        assertEquals("12 7 6 11 16 17 18 13 8 3 2 1 0 5 10 15 20 21 22 23 24 19 14 9 4 \n",
                outContent.toString());
    }

    /**
     * Tests Spiral.printSpiral metod in case of even array length.
     * The array size is small so the expected result could be predicted and compared to actual.
     *
     * @throws Exception
     * @see Spiral#printSpiral(int[][], PrintStream)
     */
    @Test
    public void printSpiralEvenSize() throws Exception {
        final int n = 4;
        int[][] array = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = i * n + j;
            }
        }
        Spiral.printSpiral(array, System.out);
        assertEquals("10 6 5 9 13 14 15 11 7 3 2 1 0 4 8 12 \n",
                outContent.toString());
    }

    /**
     * Test Spiral.sort in case of simple given array.
     *
     * @throws Exception
     * @see Spiral#sort(int[][])
     */
    @Test
    public void sortSimple() throws Exception {
        int[][] array = {{3, 1}, {4, 2}};
        Spiral.sort(array);
        int[][] expected = {{1, 3}, {2, 4}};
        assertArrayEquals(expected, array);
    }


    /**
     * Tests Spiral.sort if all the rows are the same and each contains all numbers from n to 1.
     * The result should also have all rows equal and each should contain all numbers from 1 to n.
     *
     * @throws Exception
     * @see Spiral#sort(int[][])
     */
    @Test
    public void sortEqualRows() throws Exception {
        final int n = 11;
        int[][] array = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = n - j;
            }
        }
        Spiral.sort(array);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                assertEquals(j + 1, array[i][j]);
            }
        }

    }

    /**
     * Tests Spiral.sort on array filled with random numbers.
     * Checks if the columns go in the correct order after sorting by comparing near-by elements in the first row.
     *
     * @throws Exception
     * @see Spiral#sort(int[][])
     */
    @Test
    public void sortRandomArray() throws Exception {
        Random rand = new Random();
        final int n = 30;
        int[][] array = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = rand.nextInt();
            }
        }
        Spiral.sort(array);
        for (int i = 1; i < n; i++) {
            assertTrue(array[0][i] >= array[0][i - 1]);
        }

    }

    /**
     * Tests Spiral.sort on the array consisting of one element.
     *
     * @throws Exception
     */
    @Test
    public void sortSmallArray() throws Exception {
        int[][] array = {{1}};
        Spiral.sort(array);
        int[][] expected = {{1}};
        assertArrayEquals(expected, array);

    }

    /**
     * Tests Spiral.sort in case of array filled with equal values.
     *
     * @throws Exception
     */
    @Test
    public void sortArrayWithEqualElements() throws Exception {
        int[][] array = {{0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}};
        Spiral.sort(array);
        int[][] expected = {{0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}};
        assertArrayEquals(expected, array);

    }


}
