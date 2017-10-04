package ru.spbau202.lupuleac.Trie;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

public class TrieTest {
    @Test
    public void add() throws Exception {
        Trie trie = new Trie();
        assertTrue(trie.add("hello"));
    }

    @Test
    public void addEmpty() throws Exception {
        Trie trie = new Trie();
        trie.add("");
        assertTrue(trie.contains(""));
    }

    @Test
    public void emptyDoesNotContainEmptystring() {
        Trie trie = new Trie();
        assertFalse(trie.contains(""));
    }

    @Test
    public void doesNotContainPrefixAsAnElement() throws Exception {
        Trie trie = new Trie();
        trie.add("hello");
        assertFalse(trie.contains("hell"));
    }

    @Test
    public void doesNotContainEmpty() throws Exception {
        Trie trie = new Trie();
        trie.add("lilly");
        assertFalse(trie.contains(""));
    }

    @Test
    public void addSeveralStrings() throws Exception {
        Trie trie = new Trie();
        trie.add("hers");
        trie.add("his");
        trie.add("he");
        trie.add("she");
        assertTrue(trie.contains("hers"));
        assertTrue(trie.contains("his"));
        assertTrue(trie.contains("he"));
        assertTrue(trie.contains("she"));
    }

    @Test
    public void contains() throws Exception {
        Trie trie = new Trie();
        trie.add("some string");
        assertTrue(trie.contains("some string"));
    }

    @Test
    public void notContains() throws Exception {
        Trie trie = new Trie();
        trie.add("some string");
        assertFalse(trie.contains("some"));
    }

    @Test
    public void remove() throws Exception {
        Trie trie = new Trie();
        trie.add("something");
        assertTrue(trie.remove("something"));
        assertFalse(trie.contains("something"));
    }

    @Test
    public void removeAbsentElement() throws Exception {
        Trie trie = new Trie();
        assertFalse(trie.remove("something"));
    }

    @Test
    public void howManyStartsWithPrefix() throws Exception {
        Trie trie = new Trie();
        trie.add("hello");
        trie.add("hi");
        trie.add("he");
        trie.add("she");
        assertEquals(2, trie.howManyStartsWithPrefix("he"));
    }

    @Test
    public void sizeOfEmpty() throws Exception {
        Trie trie = new Trie();
        assertEquals(0, trie.size());
    }

    @Test
    public void size() throws Exception {
        Trie trie = new Trie();
        trie.add("carmen");
        trie.add("car");
        trie.add("carma");
        assertEquals(3, trie.size());
    }

    @Test
    public void addEmptyStringsSize() throws Exception {
        Trie trie = new Trie();
        trie.add("");
        trie.add("");
        assertEquals(1, trie.size());
    }

    @Test
    public void addEqual() throws Exception {
        Trie trie = new Trie();
        trie.add("mama");
        trie.add("mama");
        assertEquals(1, trie.size());
        assertTrue(trie.contains("mama"));
    }

    @Test
    public void stringWithOneCharacter() throws Exception {
        Trie trie = new Trie();
        StringBuilder stringToAddOneCharacter = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringToAddOneCharacter.append("o");
            trie.add(stringToAddOneCharacter.toString());
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            result.append("o");
            assertTrue(trie.contains(result.toString()));
        }
    }

    @Test
    public void serializeAndDeserialize() throws Exception {
        Trie trie = new Trie();
        trie.add("first");
        trie.add("second");
        trie.add("third");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        trie.serialize(bos);
        Trie anotherTrie = new Trie();
        anotherTrie.deserialize(new ByteArrayInputStream(bos.toByteArray()));
        assertEquals(3, anotherTrie.size());
        assertTrue(anotherTrie.contains("first"));
        assertTrue(anotherTrie.contains("second"));
        assertTrue(anotherTrie.contains("third"));

    }

    @Test
    public void unusualCharacters() throws Exception {
        Trie trie = new Trie();
        trie.add("строчка с кириллицей");
        trie.add(" α, ε, η, ι, ο, υ, ω");
        assertTrue(trie.contains("строчка с кириллицей"));
        assertTrue(trie.contains(" α, ε, η, ι, ο, υ, ω"));
    }


}