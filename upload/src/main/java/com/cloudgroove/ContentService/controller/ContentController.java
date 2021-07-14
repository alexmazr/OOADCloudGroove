package com.cloudgroove.ContentService.controller;

import com.cloudgroove.ContentService.util.DeliveryService;
import com.cloudgroove.ContentService.util.DeliveryServiceFactory;
import com.cloudgroove.ContentService.util.UploadService;
import com.cloudgroove.ContentService.util.UploadServiceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ContentController
{

    @Value("${cloudgroove.upload.provider}")
    private String provider;

    @Value("${cloudgroove.localservice.ip}")
    private String localServiceIp;

    @Value("${cloudgroove.songservice.port}")
    private Integer songServicePort;

    @RequestMapping(path = "/api/upload", method = RequestMethod.POST)
    public HttpStatus uploadPost (@RequestParam("file") MultipartFile file, @RequestParam("title") String title, @RequestParam("artist") String artist, @RequestParam("userId") String userId)
    {
        // Attempt to perform the file upload
        UploadService uploader = UploadServiceFactory.create (provider);
        uploader.init ();
        String path = uploader.upload (file, userId);
        if (path == null ) return HttpStatus.BAD_REQUEST;

        // Attempt to upload file metadata to the playlist API
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("title", title);
        body.add("artist", artist);
        body.add("filepath", path);
        body.add("ownerId", userId);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject("http://"+localServiceIp+":"+songServicePort+"/api/add/song/", requestEntity,String.class);
        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(path = "/api/user/{userId}/download/{fileName}", method = RequestMethod.GET)
    public String downloadGet (@PathVariable("userId") String userId, @PathVariable("fileName") String fileName)
    {
        // Attempt to get the content URL
        DeliveryService delivery = DeliveryServiceFactory.create (provider);
        delivery.init ();
        return delivery.download(userId, fileName);
    }
}
