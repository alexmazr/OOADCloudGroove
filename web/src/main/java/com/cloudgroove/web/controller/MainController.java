package com.cloudgroove.web.controller;


import com.cloudgroove.web.model.SongList;
import com.cloudgroove.web.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
public class MainController
{
    @Value("${cloudgroove.songservice.ip}")
    private String songServiceIp;

    @Value("${cloudgroove.songservice.port}")
    private Integer songServicePort;

    @Value("${cloudgroove.uploadservice.ip}")
    private String uploadServiceIp;

    @Value("${cloudgroove.uploadservice.port}")
    private Integer uploadServicePort;

    @GetMapping("/")
    public String indexPage ()
    {
        return "index";
    }

    @GetMapping("/user/{userID}")
    public String userPage (@PathVariable("userID") String userID, Model model)
    {
        RestTemplate restTemplate = new RestTemplate();
        User thisUser = restTemplate.getForObject("http://"+songServiceIp+":"+songServicePort+"/api/user/" +userID, User.class);

       /* Playlist playlists = Playlist.builder()
                .name()
                .ownerId()
                .playlistId()
                .build();

        */

        model.addAttribute("user", thisUser);


        return "userHome";
    }

    @GetMapping("/user/{userID}/playlist/{playlistID}/{playlistName}")
    public String userHome (@PathVariable("playlistID") String playlistId, @PathVariable("playlistName") String playlistName, Model model)
    {
        RestTemplate restTemplate = new RestTemplate();
        SongList songList = restTemplate.getForObject("http://"+songServiceIp+":"+songServicePort+"/api/playlist/" +playlistId, SongList.class);

        model.addAttribute("songList", songList);
        model.addAttribute("playlistName", playlistName);

        return "playlist";
    }

    @PostMapping("/user/{userID}/new-playlist")
    public String createNewPlaylist (@PathVariable("userID") String userID, Model model) { return "index"; }

    @PostMapping("/user/{userID}/playlist/{playlistID}/{playlistName}/upload")
    public String upload (@PathVariable("playlistID") String playlistId, @PathVariable("playlistName") String playlistName, @RequestParam("newUpload") MultipartFile file, Model model)
    {
        RestTemplate restTemplate = new RestTemplate();

        // Create HTTP header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Attempt to get a file and add it to our request body, return error if it fails
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // Attempt to add the file as a ByteArrayResource
        try { body.add("file", new ByteArrayResource(file.getBytes())); }
        catch (Exception e) {
            System.out.println(e);
            return "error";
        }

        // Add other file meta-data
        body.add ("name", file.getOriginalFilename());
        body.add ("size", file.getSize());

        // Create a new HttpEntity
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        // Send the post request to our API
        HttpStatus result = restTemplate.postForObject("http://"+uploadServiceIp+":"+uploadServicePort+"/api/upload/", request, HttpStatus.class);
        System.out.println(result);
        return "index";
    }

    @PostMapping("/login")
    public String userLogin (Model model)
    {
        return "index";
    }

    @PostMapping("/signup")
    public String userSignup (Model model)
    {
        return "index";
    }

//    @GetMapping("/greetings/{name}")
//    public String indexPage (@PathVariable("name") String name, Model model)
//    {
//        model.addAttribute(name);
//        return "name";
//    }
}
