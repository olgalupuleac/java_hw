package ru.spbau202.lupuleac.Spiral;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Class Spiral represents two public static methods to work with two-dimensional array.
 * First method printSpiral prints elements in spiral order, second method sort sorts array columns comparing
 * their first elements.
 */
public class Spiral {
    /**
     * Returns true if indexes are not out of array bonds
     * (array height is equal to array length).
     *
     * @param row    is row index
     * @param column is column index
     * @param n      is array length and height
     * @return true if indexes are not out of bonds
     */
    private static boolean notOutOfBonds(int row, int column, int n) {
        return row >= 0 && column >= 0 && row < n && column < n;
    }

    /**
     * Prints 2D array of integers in spiral order starting from the center.
     * Array height and length should be equal and could be either odd or even
     * (if it is even first will be printed one of the central elements).
     * To avoid array overrun method checks if row and column indexes of an element
     * to be printed next are not out of bonds.
     * If row and column index is out of bonds, the whole array is already printed.
     *
     * @param array is two-dimensional array to be printed
     * @see Spiral#notOutOfBonds(int, int, int)
     */
    public static void printSpiral(int[][] array) {
        int x = array.length / 2;
        int y = array.length / 2;
        final int n = array.length;
        for (int k = 1; k <= n; k += 2) {
            for (int i = 0; i < k && notOutOfBonds(x, y, n); i++) {
                System.out.printf("%d ", array[x--][y]);
            }
            for (int i = 0; i < k && notOutOfBonds(x, y, n); i++) {
                System.out.printf("%d ", array[x][y--]);
            }
            for (int i = 0; i < k + 1 && notOutOfBonds(x, y, n); i++) {
                System.out.printf("%d ", array[x++][y]);
            }
            for (int i = 0; i < k + 1 && notOutOfBonds(x, y, n); i++) {
                System.out.printf("%d ", array[x][y++]);
            }
        }
        System.out.printf("\n");
    }

    /**
     * Sorts 2D array columns comparing their first elements.
     * Method creates reversed copy of array to sort it by rows
     * and then changes values in given array reversing sorted array again.
     *
     * @param array is array to be sorted
     */
    public static void sort(int[][] array) {
        int n = array.length;
        int[][] reversedArray = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                reversedArray[i][j] = array[j][i];
            }
        }
        Arrays.sort(reversedArray, Comparator.comparingInt(o -> o[0]));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = reversedArray[j][i];
            }
        }
    }
}
