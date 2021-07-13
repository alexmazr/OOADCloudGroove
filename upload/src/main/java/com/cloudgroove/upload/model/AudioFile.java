package com.cloudgroove.upload.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AudioFile
{
    private String fileName;
    private byte[] bytes;
    private long size;
}
