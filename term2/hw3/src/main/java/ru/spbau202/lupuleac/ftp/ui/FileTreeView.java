package ru.spbau202.lupuleac.ftp.ui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.ftp.FileTransferClient;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Class which represents UI for FileTransferClient.
 */
public class FileTreeView extends Application {
    private FileTransferClient client;
    private static int portNumber;
    private static String host;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println(
                    "Incorrect usage: arguments should be port number and host name");
            return;
        }
        portNumber = Integer.parseInt(args[0]);
        host = args[1];
        launch(args);
    }

    /**
     * Starts the application. Shows the root directory.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            client = new FileTransferClient(host, portNumber);
        } catch (IOException e) {
            showExceptionWindow("Exception while trying to connect to server\n", e);
            return;
        }
        primaryStage.setTitle("Files list");
        FileTreeItem rootItem = new FileTreeItem(new FileTransferClient.FileInfo("", true));
        rootItem.setExpanded(true);
        TreeView<FileTransferClient.FileInfo> tree = new TreeView<>(rootItem);
        tree.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (!v.getValue().isLeaf()) {
                return;
            }
            String path = ((FileTreeItem) v.getValue()).getPath();
            try {
                showDownloadDialog(path);
                client.get(path);
            } catch (IOException e) {
                showExceptionWindow("Error while downloading file " + path, e);
            }
        });
        StackPane root = new StackPane();
        root.getChildren().add(tree);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    /**
     * Class which represents an of the tree (a file or a directory).
     */
    private class FileTreeItem extends TreeItem<FileTransferClient.FileInfo> {
        private FileTransferClient.FileInfo fileInfo;
        private boolean wasListed = false;

        private FileTreeItem(FileTransferClient.FileInfo fileInfo) {
            super(fileInfo);
            this.fileInfo = fileInfo;
        }

        @Override
        public ObservableList<TreeItem<FileTransferClient.FileInfo>> getChildren() {
            if (isLeaf() || wasListed) {
                if (isLeaf()) {
                    System.err.println("leaf " + fileInfo.getName());
                }
                return super.getChildren();
            }
            String path = getPath();
            try {
                wasListed = true;
                client.list(getPath())
                        .stream()
                        .map(FileTreeItem::new)
                        .forEach(super.getChildren()::add);
            } catch (IOException e) {
                showExceptionWindow("Error while listing directory " + path, e);
            }
            return super.getChildren();
        }

        @NotNull
        private String getPath() {
            if (getParent() == null) {
                return File.separator + fileInfo.getName();
            }
            ArrayList<String> names = new ArrayList<>();
            for (FileTreeItem item = this; item != null; item = (FileTreeItem) item.getParent()) {
                names.add(0, item.fileInfo.getName());
            }
            return String.join(File.separator, names);
        }


        @Override
        public boolean isLeaf() {
            return !fileInfo.getDirectory();
        }
    }

    /**
     * Shows notification if an exception occurs.
     *
     * @param path is a path to file to be downloaded
     */
    private void showDownloadDialog(@NotNull String path) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Downloading file " + path);

        alert.showAndWait();
    }

    /**
     * Shows notification about downloading the file.
     *
     * @param msg is a message to be shown
     * @param e   is an exception which occurred
     */
    private void showExceptionWindow(@NotNull String msg, @NotNull Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText(msg);
        alert.setContentText(e.getMessage());


// Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
}