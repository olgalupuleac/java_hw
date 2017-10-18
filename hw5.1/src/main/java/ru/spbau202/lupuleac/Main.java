package ru.spbau202.lupuleac;


import com.sun.istack.internal.NotNull;
import ru.spbau202.lupuleac.Maybe.Maybe;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads integers from file, squares them and writes to another file.
 * If the line cannot be parsed as integer, writes "null" instead.
 */
public class Main {
    /**
     * The method which implements class functionality.
     * If the number of arguments is incorrect, it prints a message and returns.
     *
     * @param args are input and output filenames.
     * @throws IOException if it occurs (given file does not exist, etc.)
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Incorrect arguments");
            return;
        }
        File in = new File(args[0]);
        ArrayList<Maybe<Integer>> numbers = getIntegersFromFile(in);
        ArrayList<Maybe<Integer>> result = getSquares(numbers);
        File out = new File(args[1]);
        writeMaybesToFile(out, result);
    }


    /**
     * Iterates over given ArrayList of Maybe with integer.
     * If value in Maybe is present it adds its square wrapped in Maybe
     * and adds empty Maybe(Nothing) otherwise.
     *
     * @param givenNumbers is the given ArrayList of Maybe
     * @return ArrayList containing Maybe of squares or Nothing.
     */
    @NotNull
    private static ArrayList<Maybe<Integer>> getSquares(ArrayList<Maybe<Integer>> givenNumbers) {
        ArrayList<Maybe<Integer>> result = new ArrayList<>();
        for (Maybe<Integer> i : givenNumbers) {
            result.add(i.map(x -> x * x));
        }
        return result;
    }

    /**
     * Reads the given file line by line and tries to parse it as an Integer.
     * It adds parsed Integer wrapped in Maybe to ArrayList result.
     * Otherwise, it adds Nothing to result.
     *
     * @param file is file to read from.
     * @return ArrayList of Maybe, containing parsed Integers or Nothing if line cannot be parsed.
     */
    @NotNull
    private static ArrayList<Maybe<Integer>> getIntegersFromFile(@NotNull File file) throws IOException {
        ArrayList<Maybe<Integer>> result = new ArrayList<>();
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                try {
                    Integer i = Integer.valueOf(line);
                    result.add(new Maybe<>(i));
                } catch (NumberFormatException e) {
                    result.add(new Maybe<Integer>());
                }
            }
        }
        return result;
    }

    /**
     * Writes Integers to file from ArrayList of Maybe.
     * If Maybe contains Integer (Just), it writes it to file.
     * If Maybe is Nothing, it writes "null" instead.
     *
     * @param file   is file to write to.
     * @param maybes is ArrayList of Maybe containing integers or Nothing to write to file.
     */
    private static void writeMaybesToFile(
            @NotNull File file, @NotNull ArrayList<Maybe<Integer>> maybes)
            throws IOException {
        try (PrintStream writer = new PrintStream(file)) {
            for (Maybe<Integer> i : maybes) {
                if (i.isPresent()) {
                    writer.println(i.get().toString());
                } else {
                    writer.println("null");
                }
            }
            writer.flush();
        }
    }

}
