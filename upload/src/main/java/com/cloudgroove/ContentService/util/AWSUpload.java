package com.cloudgroove.ContentService.util;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class AWSUpload implements UploadService
{
    private String S3URL = "http://cloudgroove.s3-website.us-east-2.amazonaws.com";

    @Autowired
    private AmazonS3 s3Client;

    public boolean init ()
    {
        return true;
    }

    public String upload (MultipartFile file, String userId)
    {
        return "";
    }


}
