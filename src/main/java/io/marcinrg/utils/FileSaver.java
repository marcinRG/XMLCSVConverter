package io.marcinrg.utils;

import io.marcinrg.interfaces.IGetData;

import java.io.*;
import java.util.List;

public class FileSaver {

    public static <T extends IGetData> boolean saveToFile(File file, List<T> list, String delimiter, boolean changeNumbersToPLEncoding) {
        try {
            if (list.size() > 0) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
                    for (T elem : list) {
                        writer.write(elem.getData(delimiter, changeNumbersToPLEncoding));
                        writer.newLine();
                    }
                }

            }
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

}
