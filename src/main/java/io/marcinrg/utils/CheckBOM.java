package io.marcinrg.utils;
import org.apache.commons.io.input.BOMInputStream;
import java.io.*;

public class CheckBOM {
    public static boolean checkUTF8FileForBOM(File f) {
        boolean hasBom = false;
        try (BOMInputStream bomInputStream = new BOMInputStream(new FileInputStream(f))) {
            hasBom = bomInputStream.hasBOM();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hasBom;
    }

    public static BOMInputStream getStream(File f) {
        BOMInputStream bomInputStream = null;
        try {
            InputStream inputStream = new FileInputStream(f);
            bomInputStream = new BOMInputStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bomInputStream;
    }

    public static void getFileWithoutBom() {
        System.out.println("get file content without bom");
    }

}
