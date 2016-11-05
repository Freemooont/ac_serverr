package com.example.controllers.upload;

import com.example.entity.upload.Upload;
import com.example.repository.upload.UploadRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Timestamp;

@Controller
public class FileUploadController {

    public static final String STORAGE_PATH ="/opt/tomcat/storage";
    UploadRepository uploadRepository;

    void checkStorgeFolders(){
        if (!new File(STORAGE_PATH).exists()) {
            File file = new File(STORAGE_PATH);
            file.mkdir();
            System.out.println(file.getAbsolutePath());
        }
        if (!new File(STORAGE_PATH+"/photos").exists()) {
            File file = new File(STORAGE_PATH+"/photos");
            file.mkdir();
            System.out.println(file.getAbsoluteFile());
        }


        if (!new File(STORAGE_PATH+"/videos").exists()) {
            File file = new File(STORAGE_PATH+"/videos");
            file.mkdir();
            System.out.println(file.getAbsolutePath());
        }


        if (!new File(STORAGE_PATH+"/temp_media").exists()) {
            File file = new File(STORAGE_PATH+"/temp_media");
            file.mkdir();
            System.out.println(file.getAbsolutePath());
        }
    }


    @Autowired
    public FileUploadController(UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    private static final Logger logger = LoggerFactory
            .getLogger(FileUploadController.class);

    @RequestMapping(value = "/upload1", method = RequestMethod.POST)
    @ResponseBody
    JSONObject uploadFileHandler(@RequestParam("photo") MultipartFile file) {


        Upload fileModel = new Upload();

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                logger.info(System.getProperty("catalina.home") + File.separator);
                // Creating the directory to store file
                File dir = new File(/*rootPath + File.separator + */"temp_media/");
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                fileModel.setPath(serverFile.getName());
                fileModel.setMedia_type(1);
                fileModel.setTimestamp(new Timestamp(System.currentTimeMillis()));

                uploadRepository.save(fileModel);


                logger.info("Server File Location="
                        + serverFile.getAbsolutePath());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", fileModel.getId());
                return jsonObject;
            } catch (Exception e) {
                logger.info("Failed to upload file:" + file.getOriginalFilename());
                return new JSONObject();
            }
        } else {
            logger.info("Failed to upload file:" + file.getOriginalFilename());
            return new JSONObject();
        }
    }

    public File convertMultpartToFile(MultipartFile file) {
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

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    JSONArray uploadMultipleFileHandler(@RequestParam("media") MultipartFile[] files) {
        checkStorgeFolders();
        String message = "";
        System.out.println(files.length + " files come");
        JSONArray array = new JSONArray();
        for (int i = 0; i < files.length; i++) {
            Upload fileModel = new Upload();
            MultipartFile file = files[i];
            try {
                byte[] bytes = file.getBytes();


                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(STORAGE_PATH+"/temp_media/");

                System.out.println(System.getenv("CATALINA_HOME"));
                System.out.println(STORAGE_PATH);


                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + file.getOriginalFilename());
                System.out.println(serverFile.toString());
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                fileModel.setPath(serverFile.getName());
                String content_type = file.getContentType();
                if (content_type.equals("image/*"))
                    fileModel.setMedia_type(1);
                else if (content_type.equals("video/*"))
                    fileModel.setMedia_type(2);
                else fileModel.setMedia_type(3);
                fileModel.setTimestamp(new Timestamp(System.currentTimeMillis()));

                uploadRepository.save(fileModel);


                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", fileModel.getId());
                array.add(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONArray();
            }
        }
        return array;
    }

}