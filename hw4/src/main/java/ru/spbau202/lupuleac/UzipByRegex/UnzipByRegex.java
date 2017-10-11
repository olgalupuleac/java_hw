package ru.spbau202.lupuleac.UzipByRegex;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

/**
 * Class UnzipByRegex is implemented to
 * unzip zip-files in certain directory which match the given regular expression.
 */
public class UnzipByRegex {
    private String path;

    UnzipByRegex(String path) {
        this.path = path;
    }

    /**
     * Returns the list of zip files in the path.
     * To identify zip-file it checks its extension.
     *
     * @return the ArrayList containing files which might be zip-files
     */
    private ArrayList<File> getZipFiles() {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        ArrayList<File> zipFiles = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".zip")) {
                zipFiles.add(file);
            }
        }
        return zipFiles;
    }


    /**
     * Unzips the given file.
     * If the file cannot be unzipped, prints a message.
     *
     * @param file is file to unzip
     */
    private void unzip(File file) {
        String fileNameWithOutExt = FilenameUtils.removeExtension(file.getAbsolutePath());
        File dir = new File(fileNameWithOutExt);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ZipInputStream zipInputStream = new ZipInputStream(fileInputStream)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                File entryFile = new File(fileNameWithOutExt, entry.getName());
                if (entry.isDirectory()) {
                    entryFile.mkdirs();
                } else {
                    if (entryFile.getParentFile() != null && !entryFile.getParentFile().exists()) {
                        entryFile.getParentFile().mkdirs();
                    }
                    if (!entryFile.exists()) {
                        entryFile.createNewFile();
                    }
                    try (OutputStream os = new FileOutputStream(entryFile)) {
                        IOUtils.copy(zipInputStream, os);
                    }
                }
            }
        } catch (IOException e) {
            System.err.printf("Cannot unzip file ");
            System.err.println(file.getName());
        }
    }

    /**
     * Extracts all zip files in the path which names without extension match the given regular expression.
     *
     * @param regex is regular expression to match file names
     */
    public void extractFilesMatchesTheRegex(String regex) {
        ArrayList<File> zipFiles = getZipFiles();
        for (File file : zipFiles) {
            if (!FilenameUtils.removeExtension(
                    file.getName()).matches(regex))
                continue;
            unzip(file);
        }
    }
}
