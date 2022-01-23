package com.cloudgroove.web.controller;




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

    private final String podName = System.getenv().getOrDefault("POD_NAME", "NONE");
    private final String songServiceHost = System.getenv().getOrDefault("SONGSERVICE_HOST", "NONE");
    private final String uploadServiceHost = System.getenv().getOrDefault("UPLOADSERVICE_HOST", "NONE");
    private final String userServiceHost = System.getenv().getOrDefault("USERSERVICE_HOST", "NONE");

    private final Integer songServicePort = Integer.parseInt(System.getenv().getOrDefault("SONGSERVICE_PORT", "0"));
    private final Integer uploadServicePort = Integer.parseInt(System.getenv().getOrDefault("UPLOADSERVICE_PORT", "0"));
    private final Integer userServicePort = Integer.parseInt(System.getenv().getOrDefault("USERSERVICE_PORT", "0"));

    @GetMapping("/")
    public String indexPage (Model model)
    {
        model.addAttribute("POD_NAME", podName);
        model.addAttribute("SONGSERVICE_HOST", songServiceHost);
        model.addAttribute("UPLOADSERVICE_HOST", uploadServiceHost);
        model.addAttribute("USERSERVICE_HOST", userServiceHost);
        model.addAttribute("SONGSERVICE_PORT", songServicePort);
        model.addAttribute("UPLOADSERVICE_PORT", uploadServicePort);
        model.addAttribute("USERSERVICE_PORT", userServicePort);
        return "index";
    }

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/user/{userID}")
    public String userPage (@PathVariable("userID") String userID, Model model)
    {
        // Load a user page
        // Asked the user and song service for user and song data
        RestTemplate restTemplate = new RestTemplate();
        PlaylistWrapper playlists = restTemplate.getForObject("http://"+songServiceHost+":"+songServicePort+"/api/playlists/" +userID, PlaylistWrapper.class);
        List<Song> songs = restTemplate.getForObject("http://"+songServiceHost+":"+songServicePort+"/api/user/"+userID+"/songs", List.class);

        // Fills our a model and passes it to the correct view
        model.addAttribute("playlists", playlists.getPlaylists());
        model.addAttribute("songs", songs);
        model.addAttribute("userID", userID);

        return "userHome";
    }

    // This method is for loading a specific users playlist
    @GetMapping("/user/{userID}/playlist/{playlistID}/{playlistName}")
    public String userHome (@PathVariable("playlistID") String playlistId, @PathVariable("playlistName") String playlistName, Model model)
    {
        RestTemplate restTemplate = new RestTemplate();

        SongWrapper platlistItems = restTemplate.getForObject("http://"+songServiceHost+":"+songServicePort+"/api/playlist/" +playlistId, SongWrapper.class);

        model.addAttribute("songList", platlistItems.getSongs());
        model.addAttribute("playlistName", playlistName);

        return "playlist";
    }

    // A placeholder for creating a new user playlist
    @PostMapping("/user/{userID}/new-playlist")
    public String createNewPlaylist (@PathVariable("userID") String userID, Model model) { return "index"; }

    // This method asks the content service to upload a song
    @PostMapping("/user/upload")
    public String upload (@RequestParam("userID") String userID, @RequestParam("newUpload") MultipartFile file, @RequestParam("title") String title, @RequestParam("artist") String artist, Model model) throws IOException {

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
        String serverUrl = "http://"+uploadServiceHost+":"+uploadServicePort+"/api/upload/";
        HttpStatus response = restTemplate.postForObject(serverUrl, requestEntity, HttpStatus.class);
        return "redirect:/user/" + userID;
    }

    // Ask the content service to download a song
    @GetMapping("/user/{userId}/song/{fileName}")
    public String userSongPlayer (@PathVariable("userId") String userId, @PathVariable("fileName") String fileName, Model model)
    {
        RestTemplate restTemplate = new RestTemplate();
        // First, get the songId from the song that belongs to this user and has this filename
        Song song = restTemplate.getForObject("http://"+songServiceHost+":"+songServicePort+"/api/user/"+userId+"/song/"+fileName, Song.class);
        // Ask the content service for the song link
        String songLink = restTemplate.getForObject("http://"+uploadServiceHost+":"+uploadServicePort+"/api/user/"+userId+"/download/"+song.getSongId(), String.class);
        // Add the song link to the model
        model.addAttribute("songLink", songLink);
        model.addAttribute("songTitle", fileName);

        // Load other data for the userHome page
        PlaylistWrapper playlists = restTemplate.getForObject("http://"+songServiceHost+":"+songServicePort+"/api/playlists/" +userId, PlaylistWrapper.class);
        List<Song> songs = restTemplate.getForObject("http://"+songServiceHost+":"+songServicePort+"/api/user/"+userId+"/songs", List.class);

        model.addAttribute("playlists", playlists.getPlaylists());
        model.addAttribute("songs", songs);
        model.addAttribute("userID", userId);

        return "userHome";
    }

    // Handles a user login request
    @PostMapping("/login")
    public String userLogin (@RequestParam("email") String email, @RequestParam("password") String password, Model model)
    {
        // Fill out an HTTP request with login data
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("email", email);
        // Password is plaintext, this is bad, if we had more time it wouldn't ne
        body.add("password", password);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        // Ask the user service if the user logged in, if it was successful send
        // the user to their page. Otherwise leave them on the login page.
        RestTemplate restTemplate = new RestTemplate();
        String serverUrl = "http://"+userServiceHost+":"+userServicePort+"/api/login/";
        String response = restTemplate.postForObject(serverUrl, request, String.class);
        if (response.equals("failure-dne") || response.equals("failure-password")) return "index";
        else return "redirect:/user/" + response;
    }

    // Process a user signup
    @PostMapping("/signup")
    public String userSignup (@RequestParam("email") String email, @RequestParam("password") String password, Model model)
    {
        // Fill in an HTTP request with user signup data
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("email", email);
        body.add("password", password);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        // Ask the user service the sign a new user up
        RestTemplate restTemplate = new RestTemplate();
        String serverUrl = "http://"+userServiceHost+":"+userServicePort+"/api/signup/";
        String response = restTemplate.postForObject(serverUrl, request, String.class);
        if (response.equals("failure-ae")) return "index";
        else return "redirect:/user/" + response;
    }

}
