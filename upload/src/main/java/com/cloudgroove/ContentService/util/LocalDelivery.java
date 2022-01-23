package com.cloudgroove.ContentService.util;

import java.io.File;

public class LocalDelivery implements DeliveryService
{
    private File cwd;
    private String uploadPath;

    public boolean init()
    {
        this.cwd = new File(System.getProperty("user.dir"));
        //this.uploadPath = "file://" + this.cwd.getParent();
        this.uploadPath += "/localUploads";
        return true;
    }

    public String download (String userId, String fileName)
    {
        return "/"+userId+"/" + fileName;
    }
}
