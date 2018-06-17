package ru.spbau202.lupuleac.ftp;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.FileInputStream;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileTransferClientTest {
    private int port = 15000;

    @Before
    public void init() throws InterruptedException {
        Thread thread = new Thread(() -> {
            String[] args = {Integer.toString(port)};
            FileTransferServer.main(args);
        });
        //thread.setDaemon(true);
        thread.start();
        Thread.sleep(1000);
    }

    @Test
    public void get() throws Exception {
        try (FileTransferClient client = new FileTransferClient("localhost", port)) {
            client.get("src/test/resources/dir/file.txt");
            byte[] fileContent = IOUtils.toByteArray(new FileInputStream("downloads/src/test/resources/dir/file.txt"));
            byte[] expected = "aaaa".getBytes();
            assertArrayEquals(expected, fileContent);
            client.exit();
        }
    }

    @Test
    public void list() throws Exception {
        try (FileTransferClient client = new FileTransferClient("localhost", port)) {
            List<FileTransferClient.FileInfo> files = client.list("src/test/resources/dir");
            assertEquals(2, files.size());
            assertTrue(files.contains(new FileTransferClient.FileInfo("file.txt", false)));
            assertTrue(files.contains(new FileTransferClient.FileInfo("sub_dir", true)));
            List<FileTransferClient.FileInfo> filesSubDir = client.list("src/test/resources/dir/sub_dir");
            assertEquals(1, filesSubDir.size());
            assertTrue(filesSubDir.contains(new FileTransferClient.FileInfo("a.txt", false)));
            client.exit();
        }
    }

    @Test
    public void severalQueries() throws Exception {
        try (FileTransferClient client = new FileTransferClient("localhost", port)) {
            List<FileTransferClient.FileInfo> files = client.list("src/test/resources/dir");
            assertEquals(2, files.size());
            assertTrue(files.contains(new FileTransferClient.FileInfo("file.txt", false)));
            assertTrue(files.contains(new FileTransferClient.FileInfo("sub_dir", true)));
            client.get("src/test/resources/dir/file.txt");
            byte[] fileContent = IOUtils.toByteArray(new FileInputStream("downloads/src/test/resources/dir/file.txt"));
            byte[] expected = "aaaa".getBytes();
            assertArrayEquals(expected, fileContent);
            client.exit();
        }
    }

}