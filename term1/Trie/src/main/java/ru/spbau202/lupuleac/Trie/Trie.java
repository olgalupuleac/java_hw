package ru.spbau202.lupuleac.Trie;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Class Trie implements a container for unique Strings which allows to put and remove elements
 * in linear time of the element length.
 * The algorithm of all methods is similar: we start at the root and, looping over string,
 * pass to the next vertex relevant to the character of the string.
 * The null cannot be added to the Trie as an element.
 * The data can be also serialized or deserialized.
 */
public class Trie implements Serializable {
    private Vertex root = new Vertex();

    /**
     * Adds the string to the trie.
     * The execution time is O(n), where n is length of
     * the string to be added.
     *
     * @param element is the string to be added
     * @return true if the trie did not contain this element before
     */
    public boolean add(@NotNull String element) {
        if (contains(element)) {
            return false;
        }
        Vertex curVertex = root;
        for (char c : element.toCharArray()) {
            Vertex nextVertex = curVertex.next.get(c);
            if (nextVertex == null) {
                nextVertex = new Vertex();
                curVertex.next.put(c, nextVertex);
            }
            curVertex.howManyStartsWithPrefix++;
            curVertex = nextVertex;
        }
        curVertex.howManyStartsWithPrefix++;
        curVertex.isEndOfString = true;
        return true;
    }

    /**
     * Checks if the element is in the trie.
     * The execution time is O(n), where n is length of
     * the string which presence is to be checked.
     *
     * @param element the element whose presence in this trie is to be checked
     * @return true if this trie contains this element
     */
    public boolean contains(@NotNull String element) {
        Vertex curVertex = root;
        for (char c : element.toCharArray()) {
            Vertex nextVertex = curVertex.next.get(c);
            if (nextVertex == null) {
                return false;
            }
            curVertex = nextVertex;
        }
        return curVertex.isEndOfString;
    }

    /**
     * Removes the string from the trie if present.
     * The execution time is O(n), where n is length of
     * the string to be removed.
     *
     * @param element is the string to be removed
     * @return true if this trie contains this element, false
     * otherwise
     */
    public boolean remove(@NotNull String element) {
        if (!contains(element)) {
            return false;
        }
        Vertex curVertex = root;
        for (char c : element.toCharArray()) {
            Vertex nextVertex = curVertex.next.get(c);
            curVertex.howManyStartsWithPrefix--;
            curVertex = nextVertex;
        }
        curVertex.howManyStartsWithPrefix--;
        curVertex.isEndOfString = false;
        return true;
    }

    /**
     * Returns a number of elements in this trie which starts with the given prefix.
     * The execution time is O(n), where n is length of
     * the prefix.
     *
     * @param prefix is given prefix
     * @return the number of words starting with the prefix
     */
    public int howManyStartsWithPrefix(@NotNull String prefix) {
        Vertex curVertex = root;
        for (char c : prefix.toCharArray()) {
            Vertex nextVertex = curVertex.next.get(c);
            if (nextVertex == null) {
                return 0;
            }
            curVertex = nextVertex;
        }
        return curVertex.howManyStartsWithPrefix;
    }

    /**
     * Returns the number of elements in the trie.
     *
     * @return the number of elements in the trie
     */
    public int size() {
        return root.howManyStartsWithPrefix;
    }


    /**
     * Writes the trie to the output stream.
     * Creates PrintStream from given OutputStream and serializes the root.
     *
     * @param out is the output stream to write to
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void serialize(@NotNull OutputStream out) throws IOException {
        try (PrintStream printStream = new PrintStream(out)) {
            root.serialize(printStream);
        }
    }


    /**
     * Reads the trie from the input stream.
     * Creates a Scanner from given InputStream and deserializes the root.
     *
     * @param in is the input stream to read from
     * @throws IOException            if an I/O error occurs while reading stream header
     * @throws ClassNotFoundException if class of a serialized object cannot be found
     */
    public void deserialize(@NotNull InputStream in) throws IOException, ClassNotFoundException {
        try (Scanner reader = new Scanner(in)) {
            root.deserialize(reader);
        }
    }

    /**
     * Class represents a vertex in a Trie,
     * which keeps references to the next vertexes in the tree, a mark if it is an
     * end of the element in the trie
     * and number of elements which starts with the prefix relevant to this vertex.
     */
    private static class Vertex {
        private final int NUMBER_OF_CHARACTERS = 65536;
        private HashMap<Character, Vertex> next = new HashMap<>();
        private boolean isEndOfString;
        private int howManyStartsWithPrefix;


        /**
         * Writes the vertex to the print stream.
         * To simplify the parsing of serialized data the format is not binary.
         * The vertex is presented as number of integers.
         * First integer is 0 if the Vertex is an end of string and 1 otherwise.
         * After that go characters in HashMap next, written as integers.
         * After each character goes the serialization of the vertex which it is relevant to.
         * The integer equals to NUMBER_OF_CHARACTERS indicates the end of the vertex in the serialization.
         * Spaces are used as a delimiters.
         * The value of howManyStartsWithPrefix can be restored using other fields,
         * so it does not require serialization.
         *
         * @param out is the print stream to write to
         * @throws IOException if an I/O error occurs while writing stream header
         */
        private void serialize(@NotNull PrintStream out) throws IOException {
            out.print(isEndOfString ? 1 : 0);
            out.print(' ');
            Iterator it = next.entrySet().iterator();
            while (it.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) it.next();
                int intToPrint = (char) pair.getKey();
                out.print(intToPrint);
                out.print(' ');
                ((Vertex) pair.getValue()).serialize(out);
            }
            out.print(NUMBER_OF_CHARACTERS);
            out.print(' ');
        }

        /**
         * Reads the vertex from the given Scanner.
         * The method is opposite to {@link Trie.Vertex#serialize(PrintStream)},
         * and parsers the input data according to specified format.
         *
         * @param in is the input stream to read from
         * @throws IOException            if an I/O error occurs while reading stream header
         * @throws ClassNotFoundException if class of a serialized object cannot be found
         */
        private void deserialize(@NotNull Scanner in) throws IOException, ClassNotFoundException {
            int leaf = in.nextInt();
            howManyStartsWithPrefix = leaf;
            if (leaf != 0 && leaf != 1) {
                throw new ClassCastException();
            }
            isEndOfString = leaf != 0;
            int c = 0;
            while (true) {
                c = in.nextInt();
                //the end of this vertex
                if (c == NUMBER_OF_CHARACTERS) {
                    break;
                }
                next.put((char) c, new Vertex());
                (next.get((char) c)).deserialize(in);
                howManyStartsWithPrefix += (next.get((char) c)).howManyStartsWithPrefix;
            }
        }


    }

}
