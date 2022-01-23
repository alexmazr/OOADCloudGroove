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

    private final String songServiceHost = System.getenv().getOrDefault("SONGSERVICE_HOST", "NONE");
    private final Integer songServicePort = Integer.parseInt(System.getenv().getOrDefault("SONGSERVICE_PORT", "0"));

    // Function that uploads a file
    @RequestMapping(path = "/api/upload", method = RequestMethod.POST)
    public HttpStatus uploadPost (@RequestParam("file") MultipartFile file, @RequestParam("title") String title, @RequestParam("artist") String artist, @RequestParam("userId") String userId)
    {
        // Create a request for the song microservice to add song metadata to our database
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("title", title);
        body.add("artist", artist);
        body.add("filepath", file.getOriginalFilename());
        body.add("ownerId", userId);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Put request to song microservice
        RestTemplate restTemplate = new RestTemplate();
        String songId = restTemplate.postForObject("http://"+songServiceHost+":"+songServicePort+"/api/add/song/", requestEntity,String.class);

        // Attempt to perform the file upload
        // This uses a factory pattern, which allows us to set who our cloud provider is in
        // application settings, instead of hardcoding a provider.
        UploadService uploader = UploadServiceFactory.create (provider);
        uploader.init ();
        boolean status = uploader.upload (file, userId, songId);

        if (status == false ) return HttpStatus.BAD_REQUEST;
        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(path = "/api/user/{userId}/download/{songId}", method = RequestMethod.GET)
    public String downloadGet (@PathVariable("userId") String userId, @PathVariable("songId") String songId)
    {
        // Attempt to get the content URL
        // Uses a factory pattern to avoid hardcoding a cloud provider
        DeliveryService delivery = DeliveryServiceFactory.create (provider);
        delivery.init ();
        return delivery.download(userId, songId);
    }
}
