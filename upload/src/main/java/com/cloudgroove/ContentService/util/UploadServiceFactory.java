package com.cloudgroove.ContentService.util;

public class UploadServiceFactory
{
    public static UploadService create (String provider)
    {
        if (provider.equals("local")) return new LocalUpload();
        else return null;
    }
}
