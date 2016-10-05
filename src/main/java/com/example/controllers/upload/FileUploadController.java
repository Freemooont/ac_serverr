package com.example.controllers.upload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
public class FileUploadController {

    private static final Logger logger = LoggerFactory
            .getLogger(FileUploadController.class);

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    String uploadFileHandler(@RequestBody MultipartFile file) {
        System.out.println("<------------------------------------------------------" +
                "-------------------------------------------: UPLOADED---------------" +
                "-------------------------------------------------------------------->");
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                logger.info(System.getProperty("catalina.home")+File.separator);
                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(/*rootPath + File.separator + */"photos/");
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                logger.info("Server File Location="
                        + serverFile.getAbsolutePath());

                return "{\"message\" : \"You successfully uploaded file " + file.getOriginalFilename() + "\"}";
            } catch (Exception e) {
                return "{\"message\" : \"You failed to upload " + file.getOriginalFilename() + "\"}";
            }
        } else {
            return "{\"message\" : \"You failed to upload " + file.getOriginalFilename() + "\"}";
        }
    }

    public File convertMultpartToFile(MultipartFile file)
    {
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(convFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convFile;
    }

}