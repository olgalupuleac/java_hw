package ru.spbau202.lupuleac.ftp;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FileTransferClientTest {
    @Before
    public void init() {
        Thread thread = new Thread(() -> {
            String[] args = {"81"};
            FileTransferServer.main(args);
        });
        thread.start();
    }

    @Test
    public void get() throws Exception {
        try (FileTransferClient client = new FileTransferClient("localhost", 81)) {
            byte[] fileContent = client.get("src/test/resources/dir/file.txt");
            byte[] expected = "aaaa".getBytes();
            assertArrayEquals(expected, fileContent);
            client.exit();
        }
    }

    @Test
    public void list() throws Exception {
        try (FileTransferClient client = new FileTransferClient("localhost", 81)) {
            List<FileTransferClient.FileInfo> files = client.list("src/test/resources/dir");
            assertEquals(2, files.size());
            assertTrue(files.contains(new FileTransferClient.FileInfo("file.txt", false)));
            assertTrue(files.contains(new FileTransferClient.FileInfo("sub_dir", true)));
            client.exit();
        }
    }

    @Test
    public void severalQueries() throws Exception {
        try (FileTransferClient client = new FileTransferClient("localhost", 81)) {
            List<FileTransferClient.FileInfo> files = client.list("src/test/resources/dir");
            assertEquals(2, files.size());
            assertTrue(files.contains(new FileTransferClient.FileInfo("file.txt", false)));
            assertTrue(files.contains(new FileTransferClient.FileInfo("sub_dir", true)));
            byte[] fileContent = client.get("src/test/resources/dir/file.txt");
            byte[] expected = "aaaa".getBytes();
            assertArrayEquals(expected, fileContent);
            client.exit();
        }
    }

}