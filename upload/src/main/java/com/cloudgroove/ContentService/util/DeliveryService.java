package com.cloudgroove.ContentService.util;

import org.springframework.web.multipart.MultipartFile;

public interface DeliveryService
{
    // Allows credentials to be passed
    public boolean init ();
    public String download (String userId, String songId);
}
