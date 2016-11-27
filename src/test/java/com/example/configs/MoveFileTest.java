package com.example.configs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.File;

import static com.example.configs.MoveFile.STORAGE_PATH;
import static org.junit.Assert.*;

/**
 * Created by mike on 11/26/16.
 */

public class MoveFileTest {
    String path ="file:///opt/tomcat/storage/temp_media/" + File.separator + "test.txt";
    File f = new File(path);

    @Before
    public void setUp() throws Exception {
        f.createNewFile();
    }

    @After
    public void tearDown() throws Exception {
        f.delete();
    }

    @Test
    public void moveFileUsingStream() throws Exception {
    }

    @Test
    public void moveFile() throws Exception {
        assertTrue(MoveFile.moveFile(STORAGE_PATH + File.separator + "temp_media" + File.separator + "test.txt" , STORAGE_PATH + File.separator + "photos" + File.separator));
    }

}