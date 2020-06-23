package io.marcinrg.collections;

import io.marcinrg.model.FileWithPOM;
import io.marcinrg.utils.CheckBOM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.Arrays;


public class FileCollection {

    private final ObservableList<FileWithPOM> fileList = FXCollections.observableArrayList();

    public void clear() {
        fileList.clear();
    }

    public void getFilesFromDirectory(File file) {
        if ((file != null) && (file.isDirectory())) {
            fileList.clear();
            Arrays.stream(file.listFiles())
                    .filter(elem -> elem.getName().endsWith(".xml"))
                    .forEach(elem -> fileList.add(new FileWithPOM(elem, CheckBOM.checkUTF8FileForBOM(elem))));
        }
    }

    public ObservableList<FileWithPOM> getFileList() {
        return fileList;
    }

}
