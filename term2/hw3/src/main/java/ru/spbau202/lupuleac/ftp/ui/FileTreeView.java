package ru.spbau202.lupuleac.ftp.ui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.ftp.FileTransferClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileTreeView extends Application {
    private FileTransferClient client;
    private Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            client = new FileTransferClient("localhost", 81);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Files list");
        FileTreeItem rootItem = new FileTreeItem(new FileTransferClient.FileInfo("", true));
        rootItem.setExpanded(true);
        TreeView<FileTransferClient.FileInfo> tree = new TreeView<>(rootItem);
        tree.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if(!v.getValue().isLeaf()){
                return;
            }
            try {
                String path = ((FileTreeItem)v.getValue()).getPath();
                showDownloadDialog(path);
                client.get(path);
            } catch (IOException e) {
                //TODO
                e.printStackTrace();
            }
        });
        StackPane root = new StackPane();
        root.getChildren().add(tree);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    private class FileTreeItem extends TreeItem<FileTransferClient.FileInfo> {
        private FileTransferClient.FileInfo fileInfo;
        private boolean wasListed  = false;
        private FileTreeItem(FileTransferClient.FileInfo fileInfo) {
            super(fileInfo);
            this.fileInfo = fileInfo;
        }

        @Override
        public ObservableList<TreeItem<FileTransferClient.FileInfo>> getChildren() {
            if (isLeaf() || wasListed) {
                if(isLeaf()){
                    System.err.println("leaf " + fileInfo.getName());
                }
                return super.getChildren();
            }
                try {
                System.err.println(getPath());
                    wasListed = true;
                    client.list(getPath())
                            .stream()
                            .map(FileTreeItem::new)
                            .forEach(super.getChildren()::add);
                } catch (IOException e) {
                    //TODO
                    e.printStackTrace();
                }
            return super.getChildren();
        }

        @NotNull
        private String getPath() {
           if(getParent() == null){
               return File.separator + fileInfo.getName();
           }
           ArrayList<String> names = new ArrayList<>();
           for(FileTreeItem item = this; item != null; item = (FileTreeItem)item.getParent()){
               names.add(0, item.fileInfo.getName());
           }
           return String.join(File.separator, names);
        }


        @Override
        public boolean isLeaf(){
            return !fileInfo.getDirectory();
        }
    }

    private void showDownloadDialog(String string){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        Pane dialogVBox = new VBox();
        dialogVBox.getChildren().add(new Text("Downloading file " + string));
        Scene dialogScene = new Scene(dialogVBox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}