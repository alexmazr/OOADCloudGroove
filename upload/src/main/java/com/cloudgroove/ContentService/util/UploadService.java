package com.cloudgroove.ContentService.util;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService
{
    // Allows credentials to be passed
    public boolean init ();
    public String upload (MultipartFile file, String userId);
}
