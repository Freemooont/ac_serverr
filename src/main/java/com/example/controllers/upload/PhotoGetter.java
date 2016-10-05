package com.example.controllers.upload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class PhotoGetter {
    private static final Logger logger = LoggerFactory
            .getLogger(FileUploadController.class);

    @RequestMapping(value = "/photo",method = RequestMethod.GET)
    @ResponseBody
    InputStream uploadFileHandler() {

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("/home/freem/ic_launcher.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStream;

    }
    @RequestMapping(value = "/getImage/{image}", method = RequestMethod.GET)
    public void showImage(HttpServletResponse response, @PathVariable String image) throws Exception {

        InputStream inputStream = new BufferedInputStream(
                new FileInputStream(new File("/home/freem/IdeaProjects/spring-cat/photos/" + image + ".png")));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

}
