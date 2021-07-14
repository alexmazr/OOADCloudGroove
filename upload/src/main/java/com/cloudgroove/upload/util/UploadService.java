package com.cloudgroove.upload.util;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService
{
    // Allows credentials to be passed
    public boolean init ();
    public boolean upload (MultipartFile file);
}
