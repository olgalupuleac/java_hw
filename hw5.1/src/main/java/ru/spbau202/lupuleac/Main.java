package ru.spbau202.lupuleac;


import org.jetbrains.annotations.NotNull;
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
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Incorrect number of arguments");
            return;
        }
        File in = new File(args[0]);
        ArrayList<Maybe<Integer>> numbers = null;
        try {
            numbers = getIntegersFromFile(in);
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find the input file");
            return;
        }
        ArrayList<Maybe<Integer>> result = getSquares(numbers);
        File out = new File(args[1]);
        try {
            writeMaybesToFile(out, result);
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find the output file");
        }
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
    private static ArrayList<Maybe<Integer>> getSquares(
            ArrayList<Maybe<Integer>> givenNumbers) {
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
     * @throws FileNotFoundException if the file does not exist.
     */
    @NotNull
    private static ArrayList<Maybe<Integer>> getIntegersFromFile(@NotNull File file) throws FileNotFoundException {
        ArrayList<Maybe<Integer>> result = new ArrayList<>();
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                try {
                    Integer i = Integer.valueOf(line);
                    result.add(Maybe.just(i));
                } catch (NumberFormatException e) {
                    result.add(Maybe.nothing());
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
     * @throws FileNotFoundException if the file does not exit.
     */
    private static void writeMaybesToFile(
            @NotNull File file, @NotNull ArrayList<Maybe<Integer>> maybes)
            throws FileNotFoundException {
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
