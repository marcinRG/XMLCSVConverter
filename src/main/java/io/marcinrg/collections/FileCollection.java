package io.marcinrg.collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.Arrays;


public class FileCollection {

    private final ObservableList<File> fileList = FXCollections.observableArrayList();

    public void clear() {
        fileList.clear();
    }

    public void getFilesFromDirectory(File file) {
        if ((file != null) && (file.isDirectory())) {
            fileList.clear();
            if (file.listFiles().length > 0) {
                fileList.addAll(Arrays.asList(file.listFiles()));
            }
        }
    }

    public ObservableList<File> getFileList() {
        return fileList;
    }

}
