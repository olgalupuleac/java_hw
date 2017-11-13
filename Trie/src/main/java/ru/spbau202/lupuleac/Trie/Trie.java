package ru.spbau202.lupuleac.Trie;

import org.jetbrains.annotations.NotNull;

import java.io.*;

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
            Vertex nextVertex = curVertex.next[c];
            if (nextVertex == null) {
                nextVertex = new Vertex();
                curVertex.next[c] = nextVertex;
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
            Vertex nextVertex = curVertex.next[c];
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
            Vertex nextVertex = curVertex.next[c];
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
            Vertex nextVertex = curVertex.next[c];
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
     *
     * @param out is the output stream to write to
     * @throws IOException if an I/O error occurs while writing stream header
     */
    public void serialize(@NotNull OutputStream out) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(this);
            oos.flush();
        }
    }


    /**
     * Reads the trie from the input stream.
     *
     * @param in is the input stream to read from
     * @throws IOException            if an I/O error occurs while writing stream header
     * @throws ClassNotFoundException if class of a serialized object cannot be found
     */
    public void deserialize(@NotNull InputStream in) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            Trie newTrie = (Trie) ois.readObject();
            root = newTrie.root;
        }

    }

    /**
     * Class represents a vertex in a Trie,
     * which keeps references to the next vertexes in the tree, a mark if it is an
     * end of the element in the trie
     * and number of elements which starts with the prefix relevant to this vertex.
     */
    private static class Vertex implements Serializable {
        private final int NUMBER_OF_CHARACTERS = 65536;
        private Vertex[] next = new Vertex[NUMBER_OF_CHARACTERS];
        private boolean isEndOfString;
        private int howManyStartsWithPrefix;
    }

}
