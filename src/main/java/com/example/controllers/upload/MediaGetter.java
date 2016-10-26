package com.example.controllers.upload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class MediaGetter {
    private static final Logger logger = LoggerFactory
            .getLogger(FileUploadController.class);

    public static final String STORAGE_PATH ="/opt/tomcat/storage";


    @RequestMapping(value = "/getPhoto/{name}/", method = RequestMethod.GET)
    public void getImage(HttpServletResponse response, @PathVariable String name) throws Exception {

        InputStream inputStream = new BufferedInputStream(
                new FileInputStream(new File(STORAGE_PATH+"/photos/" + name)));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @RequestMapping(value = "/getVideo/{name}/", method = RequestMethod.GET)
    public void getVideo(HttpServletResponse response, @PathVariable String name) throws Exception {

        InputStream inputStream = new BufferedInputStream(
                new FileInputStream(new File(STORAGE_PATH+"/videos/" + name)));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
}
