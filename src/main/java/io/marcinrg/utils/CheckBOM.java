package io.marcinrg.utils;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CheckBOM {
    public static boolean checkUTF8FileForBOM(File f) {
        try {
            InputStream inputStream = new FileInputStream(f);
            BOMInputStream bomInputStream = new BOMInputStream(inputStream);
            return bomInputStream.hasBOM();

        } catch (IOException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        }
        return false;
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
