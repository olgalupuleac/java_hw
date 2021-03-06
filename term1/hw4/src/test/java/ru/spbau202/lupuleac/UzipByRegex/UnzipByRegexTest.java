package ru.spbau202.lupuleac.UzipByRegex;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class UnzipByRegexTest {
    private String path = "src/test/resources/test_directory";

    @NotNull
    private String readFileToString(@NotNull File file) throws Exception {
        InputStream in = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        return out.toString();
    }

    @Test
    public void emptyDirectory() throws Exception {
        String pathToEmpty = path + File.separator + "empty";
        File file = new File(pathToEmpty);
        file.mkdirs();
        UnzipByRegex.extractFilesMatchesTheRegex("*", pathToEmpty);
        File folder = new File(pathToEmpty);
        File[] listOfFiles = folder.listFiles();
        assertEquals(0, listOfFiles.length);
    }

    @Test
    public void unzipOneFile() throws Exception {
        UnzipByRegex.extractFilesMatchesTheRegex("1", path);
        File file = new File(path + File.separator + "1"
                + File.separator + "1.txt");
        assertEquals("Hello, flower!", readFileToString(file));
    }

    @Test
    public void unzipOneSeveralFiles() throws Exception {
        UnzipByRegex.extractFilesMatchesTheRegex("[0-9]*", path);
        File file1 = new File(path + File.separator + "1"
                + File.separator + "1.txt");
        assertEquals("Hello, flower!", readFileToString(file1));
        File file2 = new File(path + File.separator + "2"
                + File.separator + "2.txt");
        assertEquals("Tests", readFileToString(file2));
    }

    @Test
    public void unzipArchiveContainsSeveralFiles() throws Exception {
        UnzipByRegex.extractFilesMatchesTheRegex("A*", path);
        File file1 = new File(path + File.separator + "AAA"
                + File.separator + "AAA" + File.separator + "A.txt");
        assertEquals("A", readFileToString(file1));
        File file2 = new File(path + File.separator + "AAA"
                + File.separator + "AAA" + File.separator + "AAAA.txt");
        assertEquals("AAAA", readFileToString(file2));
        File folder = new File(path + File.separator + "AAA");
        assertEquals(1, folder.listFiles().length);
        File subfolder = new File(path + File.separator + "AAA" + File.separator + "AAA");
        assertEquals(2, subfolder.listFiles().length);
    }

    @Test(expected = FileNotFoundException.class)
    public void notExistedPath() throws Exception {
        UnzipByRegex.extractFilesMatchesTheRegex("*",
                path + File.separator + "notADir");
    }


}