package com.vectory.utils.mongodb;

import java.io.*;

public class FileUtil {

    public static byte[] fileToByte(File file) throws IOException {
        byte[] bytes = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            bytes = new byte[(int) file.length()];
            fis.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static void bytesToFile(byte[] bFile, String fileDest) {
        try (FileOutputStream fos = new FileOutputStream(fileDest)) {
            fos.write(bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
}
