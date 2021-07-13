package com.cloudgroove.upload.util;

import com.cloudgroove.upload.model.AudioFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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

    public boolean upload (AudioFile toUpload)
    {
        try {
            File newFile = new File (uploadPath + "/" + toUpload.getFileName() + ".mp3");
            // Create newFile if it doesn't already exist
            newFile.createNewFile();

            // Create output stream
            FileOutputStream output = new FileOutputStream(newFile);
            FileInputStream input = new FileInputStream(toUpload.getFileName());

            try {
                byte[] buffer = toUpload.getBytes();
                int bytesRead = 0;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                System.out.println ("wrote: " + bytesRead);
            }
            catch (Exception e) {
                System.out.println ("wenfie");
                return false;
            }

            output.close();
            input.close();

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
