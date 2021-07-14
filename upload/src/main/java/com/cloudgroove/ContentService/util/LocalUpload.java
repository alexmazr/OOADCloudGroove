package com.cloudgroove.ContentService.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

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

    public String upload (MultipartFile file, String userId)
    {
        try {
            File userDir = new File (uploadPath + "/"+userId);
            // Create local user dir if it does not exist
            if (!userDir.exists()) userDir.mkdir();
            File newFile = new File (uploadPath + "/"+userId+"/"+ file.getOriginalFilename());
            // Create newFile if it doesn't already exist
            if (!newFile.exists()) newFile.createNewFile();
            // Transfer the multipart file into the local file
            file.transferTo(newFile);
            return file.getOriginalFilename();
        }
        catch (Exception e) {
            System.out.println (e);
            return null;
        }
    }
}
