package ru.spbau202.lupuleac.ftp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;


/**
 * Class which represents FTP Server.
 * All its methods are static and there are no need to create its instances.
 */
public class FileTransferServer {
    private static final Logger LOGGER = Logger.getLogger("Server");

    /**
     * The entry point of the class.
     * To run the server this method should be called.
     *
     * @param args should consist of one string, which is a port number
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Incorrect usage of FileTransferServer.main():" +
                    " the arguments should contain only port number.");
            return;
        }
        int portNumber = Integer.parseInt(args[0]);
        LOGGER.info("Starting server");
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                DataOutputStream out =
                        new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
        ) {
            LOGGER.info("Connection created");
            String path;
            while (true) {
                Query action = Query.fromInteger(in.readInt());
                if(action == null){
                    //it cannot occur if everything is used correctly, so it is runtime
                    throw new RuntimeException("Invalid protocol");
                }
                if(action == Query.EXIT){
                    break;
                }
                path = in.readUTF();
                LOGGER.log(Level.INFO, "action = " + action + ", path = " + path);
                if (action == Query.LIST) {
                    processList(path, out);
                }
                if (action == Query.GET) {
                    processGet(path, out);
                }
            }
        } catch (IOException e) {
            LOGGER.warning("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Process the query to get file.
     * Writes file size and file contents
     * to the ouputstream connected with the client.
     * If the file does not exist or cannot be read writes 0 as a size.
     *
     * @param path is a path to file
     * @param out  is an outputstream where to write
     */
    private static void processGet(@NotNull String path, @NotNull DataOutputStream out) {
        File file = new File(path);
        try {
            out.writeLong(file.isFile() ? file.length() : 0);
        } catch (IOException e) {
            handleIOException(e);
        }
        if (!file.isFile()) {
            return;
        }
        try (InputStream inputStream = new FileInputStream(file)) {
            int count;
            byte[] buffer = new byte[4096];
            while ((count = inputStream.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    /**
     * Process the query to list files in the directory.
     * Writes the number of items in the directory
     * and for each item writes its name and if it is a directory.
     * If the directory does not exist writes 0 as a size.
     *
     * @param path is a path to the directory
     * @param out  is an outputstream where to write
     */
    private static void processList(@NotNull String path, @NotNull DataOutputStream out) {
        File dir = new File(path);
        File[] files = dir.listFiles();

        try {
            System.err.println(files == null ? 0 : files.length);
            out.writeInt(files == null ? 0 : files.length);
        } catch (IOException e) {
            handleIOException(e);
        }
        if (files == null) {
            return;
        }
        Stream.of(files).filter(Objects::nonNull).forEach(x -> {
            try {
                System.err.println(x.getName());
                out.writeUTF(x.getName());
                out.writeBoolean(x.isDirectory());
            } catch (IOException e) {
                handleIOException(e);
            }
        });
    }

    /**
     * Handles the exception which might occur while
     * writing to the outputstream.
     *
     * @param e is an exception to be handled
     */
    private static void handleIOException(IOException e) {
        LOGGER.warning("IOException occurred while sending data to client");
        LOGGER.warning(e.getMessage());
        e.printStackTrace();
    }

}

