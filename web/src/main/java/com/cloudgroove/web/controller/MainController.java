package com.cloudgroove.web.controller;


import com.cloudgroove.web.model.PlaylistItemWrapper;
import com.cloudgroove.web.model.PlaylistWrapper;
import com.cloudgroove.web.model.Song;
import com.cloudgroove.web.model.SongWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class MainController
{
    @Value("${cloudgroove.localservice.ip}")
    private String localServiceIp;

    @Value("${cloudgroove.songservice.port}")
    private Integer songServicePort;

    @Value("${cloudgroove.uploadservice.port}")
    private Integer uploadServicePort;

    @Value("${cloudgroove.userservice.port}")
    private Integer userServicePort;

    @GetMapping("/")
    public String indexPage ()
    {
        return "index";
    }

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/user/{userID}")
    public String userPage (@PathVariable("userID") String userID, Model model)
    {
        RestTemplate restTemplate = new RestTemplate();
        PlaylistWrapper playlists = restTemplate.getForObject("http://"+localServiceIp+":"+songServicePort+"/api/playlists/" +userID, PlaylistWrapper.class);
        List<Song> songs = restTemplate.getForObject("http://"+localServiceIp+":"+songServicePort+"/api/user/"+userID+"/songs", List.class);

        model.addAttribute("playlists", playlists.getPlaylists());
        model.addAttribute("songs", songs);
        model.addAttribute("userID", userID);

        return "userHome";
    }

    @GetMapping("/user/{userID}/playlist/{playlistID}/{playlistName}")
    public String userHome (@PathVariable("playlistID") String playlistId, @PathVariable("playlistName") String playlistName, Model model)
    {
        RestTemplate restTemplate = new RestTemplate();

        SongWrapper platlistItems = restTemplate.getForObject("http://"+localServiceIp+":"+songServicePort+"/api/playlist/" +playlistId, SongWrapper.class);

        model.addAttribute("songList", platlistItems.getSongs());
        model.addAttribute("playlistName", playlistName);

        return "playlist";
    }

    @PostMapping("/user/{userID}/new-playlist")
    public String createNewPlaylist (@PathVariable("userID") String userID, Model model) { return "index"; }

    @PostMapping("/user/upload")
    public String upload (@RequestParam("userID") String userID, @RequestParam("newUpload") MultipartFile file, @RequestParam("title") String title, @RequestParam("artist") String artist, Model model) throws IOException {

        // TODO: Need to verify user session

        //Set the header to be multipart form data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Add the file resource to the body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());
        body.add("userId", userID);
        body.add("title", title);
        body.add("artist", artist);

        // Create the request entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Send the file resource to the upload service
        RestTemplate restTemplate = new RestTemplate();
        String serverUrl = "http://"+localServiceIp+":"+uploadServicePort+"/api/upload/";
        HttpStatus response = restTemplate.postForObject(serverUrl, requestEntity, HttpStatus.class);
        return "redirect:/user/" + userID;
    }

    @GetMapping("/user/{userId}/song/{fileName}")
    public String userSongPlayer (@PathVariable("userId") String userId, @PathVariable("fileName") String fileName, Model model)
    {
        RestTemplate restTemplate = new RestTemplate();
        // First, get the songId from the song that belongs to this user and has this filename
        Song song = restTemplate.getForObject("http://"+localServiceIp+":"+songServicePort+"/api/user/"+userId+"/song/"+fileName, Song.class);
        // Ask the content service for the song link
        String songLink = restTemplate.getForObject("http://"+localServiceIp+":"+uploadServicePort+"/api/user/"+userId+"/download/"+song.getSongId(), String.class);
        // Add the song link to the model
        model.addAttribute("songLink", songLink);
        model.addAttribute("songTitle", fileName);

        // Load other data for the userHome page
        PlaylistWrapper playlists = restTemplate.getForObject("http://"+localServiceIp+":"+songServicePort+"/api/playlists/" +userId, PlaylistWrapper.class);
        List<Song> songs = restTemplate.getForObject("http://"+localServiceIp+":"+songServicePort+"/api/user/"+userId+"/songs", List.class);

        model.addAttribute("playlists", playlists.getPlaylists());
        model.addAttribute("songs", songs);
        model.addAttribute("userID", userId);

        return "userHome";
    }

    @PostMapping("/login")
    public String userLogin (@RequestParam("email") String email, @RequestParam("password") String password, Model model)
    {
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("email", email);
        body.add("password", password);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        String serverUrl = "http://"+localServiceIp+":"+userServicePort+"/api/login/";
        String response = restTemplate.postForObject(serverUrl, request, String.class);
        if (response.equals("failure-dne") || response.equals("failure-password")) return "index";
        else return "redirect:/user/" + response;
    }

    @PostMapping("/signup")
    public String userSignup (@RequestParam("email") String email, @RequestParam("password") String password, Model model)
    {
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("email", email);
        body.add("password", password);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        String serverUrl = "http://"+localServiceIp+":"+userServicePort+"/api/signup/";
        String response = restTemplate.postForObject(serverUrl, request, String.class);
        if (response.equals("failure-ae")) return "index";
        else return "redirect:/user/" + response;
    }

}
