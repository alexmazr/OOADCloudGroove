package com.cloudgroove.upload.controller;

import com.cloudgroove.upload.model.AudioFile;
import com.cloudgroove.upload.util.AudioFileFactory;
import com.cloudgroove.upload.util.LocalUpload;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.FileSystem;

@RestController
public class UploadController
{
    @PostMapping(value = "/api/upload", consumes = {"multipart/form-data"})
    public HttpStatus uploadPost (@RequestParam(name="file") String file, @RequestParam(name="name") String name, @RequestParam(name="size") long size)
    {
       // Refactor this to be a factory that checks credentials
        System.out.println (file.length());
        LocalUpload uploader = new LocalUpload();
        uploader.init ();
        boolean success = uploader.upload (AudioFileFactory.create (file, name, size));
        //System.out.println (file.getFilename());
        if (success) return HttpStatus.ACCEPTED;
        else return HttpStatus.BAD_REQUEST;
    }
}
