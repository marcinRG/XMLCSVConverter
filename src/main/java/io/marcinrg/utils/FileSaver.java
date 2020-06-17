package io.marcinrg.utils;

import io.marcinrg.interfaces.IGetData;

import java.io.*;
import java.util.ArrayList;

public class FileSaver {

    public static <T extends IGetData> boolean saveToFile(File file, ArrayList<T> list) {
        try {
            if (list.size() > 0) {
                File f = file;
                FileOutputStream fout = new FileOutputStream(file);
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fout))) {
                    for (T elem : list) {
                        writer.write(elem.getData());
                        writer.newLine();
                    }
                }

            }
        } catch (IOException ex) {
        }
        return true;
    }

}
