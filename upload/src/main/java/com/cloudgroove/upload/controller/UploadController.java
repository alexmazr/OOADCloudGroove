package com.cloudgroove.upload.controller;

import com.cloudgroove.upload.util.LocalUpload;
import com.cloudgroove.upload.util.UploadService;
import com.cloudgroove.upload.util.UploadServiceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController
{

    @Value("${cloudgroover.upload.provider}")
    private String uploadProvider;

    @RequestMapping(path = "/api/upload", method = RequestMethod.POST)
    public HttpStatus uploadPost (@RequestParam("file") MultipartFile file)
    {
        UploadService uploader = UploadServiceFactory.create (uploadProvider);
        uploader.init ();
        if (uploader.upload (file)) return HttpStatus.ACCEPTED;
        else return HttpStatus.BAD_REQUEST;
    }
}
