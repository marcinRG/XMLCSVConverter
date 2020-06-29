package io.marcinrg.model;

import java.io.File;

public class FileWithPOM {
    private File file;
    private boolean hasPOM;

    public FileWithPOM(File file) {
        this.file = file;
        hasPOM = false;
    }

    public FileWithPOM(File file, boolean hasPOM) {
        this(file);
        this.hasPOM = hasPOM;
    }

    public File getFile() {
        return file;
    }

    public boolean isHasPOM() {
        return hasPOM;
    }

}
