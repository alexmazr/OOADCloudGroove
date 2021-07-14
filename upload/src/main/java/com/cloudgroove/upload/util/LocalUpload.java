package com.cloudgroove.upload.util;

import com.cloudgroove.upload.model.AudioFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class LocalUpload implements UploadService
{
    private File cwd;
    private String uploadPath;

    public boolean init()
    {
        this.cwd = new File (System.getProperty("user.dir"));
        this.uploadPath = this.cwd.getParent();
        this.uploadPath += "/localUploads";
        return true;
    }

    public boolean upload (MultipartFile file)
    {
        try {
            File newFile = new File (uploadPath + "/" + file.getOriginalFilename() + ".mp3");
            // Create newFile if it doesn't already exist
            if (!newFile.exists()) newFile.createNewFile();
            file.transferTo(newFile);
            return true;
        }
        catch (Exception e) {
            System.out.println (e);
            return false;
        }
    }
}
