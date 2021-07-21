package com.cloudgroove.ContentService.util;

public class UploadServiceFactory
{
    // Factory pattern for our upload services
    public static UploadService create (String provider)
    {
        if (provider.equals("local")) return new LocalUpload();
        else if (provider.equals("aws")) return new AWSUpload();
        else return null;
    }
}
