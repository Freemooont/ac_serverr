package com.example.configs;

import java.io.*;

/**
 * Created by mike on 11/15/16.
 */
public class MoveFile {
    //TODO Change to IOUtils
    public static void moveFileUsingStream(String source, String dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            new File(source).delete();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
            os.close();
        }
    }
}
