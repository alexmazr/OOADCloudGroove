package com.cloudgroove.ContentService.util;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class AWSDelivery implements DeliveryService
{

    public boolean init ()
    {
        return true;
    }

    public String download (String userId, String songId)
    {
        return "https://dr8cw8qdn0201.cloudfront.net/" +userId+"/"+songId;
    }
}
