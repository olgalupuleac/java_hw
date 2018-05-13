package ru.spbau202.lupuleac.ftp;


import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class which represents FTP Client.
 */
public class FileTransferClient implements AutoCloseable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private static final Logger LOGGER = Logger.getLogger("Client");

    /**
     * Creates the client connected to the specified port number on the named host.
     *
     * @param host is the host name
     * @param port the port number
     * @throws IOException if an I/O error occurs when creating the socket
     */
    public FileTransferClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        LOGGER.info("Connection created");
    }

    /**
     * Loads the file from the server.
     *
     * @param path is a path to a file to be loaded
     * @throws IOException if it occurs during the process
     */
    public void get(@NotNull String path) throws IOException {
        LOGGER.info("Getting file " + path);
        out.writeInt(2);
        out.writeUTF(path);
        long size = in.readLong();
        if (size == 0) {
            return;
        }
        File file = new File("downloads" + File.separator + path);
        file.getParentFile().mkdirs();
        file.createNewFile();
        long count = 0;
        byte[] data = new byte[16384];
        try(FileOutputStream fos = new FileOutputStream(file)){
            while (count < size) {
                int nRead = in.read(data, 0, data.length);
                fos.write(data, 0, nRead);
                count += nRead;
            }
            fos.flush();
        }
    }

    /**
     * Get the list of files in the specified directory from the server.
     *
     * @param path is a path to the directory
     * @return a list containing information about files in the directory
     * @throws IOException if it occurs during the process
     */
    public List<FileInfo> list(@NotNull String path) throws IOException {
        LOGGER.info("Listing directory " + path);
        out.writeInt(1);
        out.writeUTF(path);
        int size = in.readInt();
        List<FileInfo> files = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            FileInfo file = new FileInfo();
            file.setName(in.readUTF());
            file.setDirectory(in.readBoolean());
            files.add(file);
        }
        return files;
    }

    /**
     * Closes the connection on server.
     *
     * @throws IOException if it occurs
     */
    public void exit() throws IOException {
        out.writeInt(0);
    }

    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     * <p>
     * <p>While this interface method is declared to throw {@code
     * Exception}, implementers are <em>strongly</em> encouraged to
     * declare concrete implementations of the {@code close} method to
     * throw more specific exceptions, or to throw no exception at all
     * if the close operation cannot fail.
     * <p>
     * <p> Cases where the close operation may fail require careful
     * attention by implementers. It is strongly advised to relinquish
     * the underlying resources and to internally <em>mark</em> the
     * resource as closed, prior to throwing the exception. The {@code
     * close} method is unlikely to be invoked more than once and so
     * this ensures that the resources are released in a timely manner.
     * Furthermore it reduces problems that could arise when the resource
     * wraps, or is wrapped, by another resource.
     * <p>
     * <p><em>Implementers of this interface are also strongly advised
     * to not have the {@code close} method throw {@link
     * InterruptedException}.</em>
     * <p>
     * This exception interacts with a thread's interrupted status,
     * and runtime misbehavior is likely to occur if an {@code
     * InterruptedException} is {@linkplain Throwable#addSuppressed
     * suppressed}.
     * <p>
     * More generally, if it would cause problems for an
     * exception to be suppressed, the {@code AutoCloseable.close}
     * method should not throw it.
     * <p>
     * <p>Note that unlike the {@link Closeable#close close}
     * method of {@link Closeable}, this {@code close} method
     * is <em>not</em> required to be idempotent.  In other words,
     * calling this {@code close} method more than once may have some
     * visible side effect, unlike {@code Closeable.close} which is
     * required to have no effect if called more than once.
     * <p>
     * However, implementers of this interface are strongly encouraged
     * to make their {@code close} methods idempotent.
     *
     * @throws Exception if this resource cannot be closed
     */
    @Override
    public void close() throws Exception {
        socket.close();
        in.close();
        out.close();
    }

    /**
     * Class which represents information about files in the directory.
     */
    public static class FileInfo {
        private String name;
        private boolean isDirectory;

        public FileInfo() {
        }

        public FileInfo(String name, boolean isDirectory) {
            this.isDirectory = isDirectory;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getDirectory() {
            return isDirectory;
        }

        public void setDirectory(Boolean directory) {
            isDirectory = directory;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof FileInfo && ((FileInfo) obj).name.equals(name)
                    && ((FileInfo) obj).isDirectory == isDirectory;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}