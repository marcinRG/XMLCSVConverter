package io.marcinrg.model;

import java.io.File;

public class FileWithPOM {
    private File file;
    private boolean hasPOM;

    public FileWithPOM(File file) {
        this.file = file;
        this.hasPOM = false;
    }

    public FileWithPOM(File file, boolean hasPOM) {
        this.file = file;
        this.hasPOM = hasPOM;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isHasPOM() {
        return hasPOM;
    }

    public void setHasPOM(boolean hasPOM) {
        this.hasPOM = hasPOM;
    }
}
