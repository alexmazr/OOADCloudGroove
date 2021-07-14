package com.cloudgroove.ContentService.util;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class AWSCredentialManager
{
    private static AWSCredentials credentials = new BasicAWSCredentials(
            "blurredout",
            "blurredout"
    );

    private static String s3Bucket = "cloudgroove";

    @Autowired
    private static AmazonS3 s3Client;

    public static AmazonS3 getInstance ()
    {
        if (s3Client == null) s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_2).build();
        return s3Client;
    }

    public static String getS3Bucket ()
    {
        return s3Bucket;
    }
}
