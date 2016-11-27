package com.example.configs;

import org.hibernate.hql.internal.ast.util.PathHelper;

import java.io.*;
import java.nio.file.*;

/**
 * Created by mike on 11/15/16.
 */
public class MoveFile {
    public static String STORAGE_PATH = "/opt/tomcat/storage/";
    //TODO Change to IOUtils
    public static void moveFileUsingStream(String source, String dest) throws IOException {
        if (source == null) {
            throw new InvalidObjectException("Infalid source file");
        } else if (dest == null) {
            throw new InvalidObjectException("Invalid destination");
        } else {
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

    public static boolean moveFile(String source, String target) throws IOException {
        if (source != null || target != null) {
            Path sourcePath = FileSystems.getDefault().getPath(source);
            Path targetPath = FileSystems.getDefault().getPath(target);
            Files.move(sourcePath, targetPath, StandardCopyOption.ATOMIC_MOVE);
            return true;
        } else
            return false;
    }
}
