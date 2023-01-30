package io.marcinrg.utils;

import io.marcinrg.interfaces.IGetData;

import java.io.*;
import java.util.List;

public class FileSaver {

    public static <T extends IGetData> boolean saveToFile(File file, List<T> list, String delimiter, boolean changeNumbersToPLEncoding) {
        boolean firstLine = true;
        try {
            if (list.size() > 0) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
                    for (T elem : list) {
                        if (firstLine == true) {
                            writer.write(elem.getNames(delimiter));
                            writer.newLine();
                            firstLine = false;
                        }
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
