package io.marcinrg.collections;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class FileCollection {

    private final ArrayList<File> fileList;

    public FileCollection() {
        fileList = new ArrayList<>();
    }

    public void clear() {
        fileList.clear();
    }

    public void getFilesFromDirectory(File file) {
        if ((file != null) && (file.isDirectory())) {
            fileList.clear();
            fileList.addAll(Arrays.asList(file.listFiles()));
        }
    }

    public ArrayList<File> getFileList() {
        return fileList;
    }
}
