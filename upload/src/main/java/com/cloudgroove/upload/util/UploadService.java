package com.cloudgroove.upload.util;

import com.cloudgroove.upload.model.AudioFile;

public interface UploadService
{
    // Allows credentials to be passed
    public boolean init ();
    public boolean upload (AudioFile file);
}
