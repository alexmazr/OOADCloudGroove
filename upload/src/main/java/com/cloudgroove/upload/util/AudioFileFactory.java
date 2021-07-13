package com.cloudgroove.upload.util;

import com.cloudgroove.upload.model.AudioFile;

public class AudioFileFactory
{
    public static AudioFile create (String bytes, String fileName, long size)
    {
        return new AudioFile(fileName, bytes.getBytes(), size);
    }
}
