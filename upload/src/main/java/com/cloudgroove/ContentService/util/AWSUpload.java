package com.cloudgroove.ContentService.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AWSUpload implements UploadService
{
    private TransferManager tm;

    public boolean init ()
    {
        tm = TransferManagerBuilder.standard()
                .withS3Client(AWSCredentialManager.getInstance())
                .withMultipartUploadThreshold((long) (20000000)) // limit uploads to 20mb
                .build();
        return true;
    }

    public boolean upload (MultipartFile file, String userId, String songId)
    {
        try
        {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType("audio/mpeg");
            Upload upload = tm.upload(AWSCredentialManager.getS3Bucket(), userId+"/"+songId, file.getInputStream(), objectMetadata);
            try { upload.waitForCompletion(); }
            catch (AmazonClientException e) { return false; }
            return true;
        }
        catch (Exception e) { return false; }
    }
}
