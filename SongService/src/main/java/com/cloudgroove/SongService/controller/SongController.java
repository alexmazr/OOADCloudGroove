package com.cloudgroove.SongService.controller;

import com.cloudgroove.SongService.entity.*;
import com.cloudgroove.SongService.repository.PlaylistItemRepository;
import com.cloudgroove.SongService.repository.PlaylistRepository;
import com.cloudgroove.SongService.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class SongController {

    @Autowired
    PlaylistRepository playlistRepository;

    @Autowired
    PlaylistItemRepository playlistItemRepository;

    @Autowired
    SongRepository songRepository;

    // This function is under construction
    // Should return a list of user playlists
    @RequestMapping(value ="/api/playlists/{user_id}")
    public PlaylistWrapper userPlaylistAPI (@PathVariable("user_id") String user_id)
    {
        Optional<List<Playlist>> theseLists = playlistRepository.findAllByOwnerId(user_id);
        if (theseLists.isPresent()) {
            PlaylistWrapper playlists = PlaylistWrapper.builder()
                    .playlists(theseLists.get())
                    .build();

            return playlists;
        }
        else return null;
    }

    // Under Construction
    // Should return the songs in a single playlist
    @RequestMapping(value ="/api/playlist/{playlist_id}")
    public SongWrapper playlistAPI (@PathVariable("playlist_id") String playlist_id) {

        Optional<List<PlaylistItem>> theseItems = playlistItemRepository.findAllByPlaylistId(playlist_id);
        List<Song> songs = new ArrayList<>();

        if (theseItems.isPresent()) {
            for(PlaylistItem thisItem:theseItems.get()) {
                Song thisSong = songRepository.findBySongId(thisItem.getSongId());
                if (!ObjectUtils.isEmpty(thisSong)) {
                    songs.add(thisSong);
                }
            }
            return new SongWrapper(songs);
        }
        else return null;
    }

    // Gets a single song by it's ID
    @RequestMapping(value ="/api/song/{song_id}")
    public Song getSong (@PathVariable("song_id") String song_id)
    {
        return songRepository.findBySongId(song_id);
    }

    // Gets a users song by it's title
    @RequestMapping(value ="/api/user/{userId}/song/{title}")
    public Song getSongByName (@PathVariable("userId") String userId, @PathVariable("title") String title)
    {
        return songRepository.findByOwnerIdAndTitle(userId, title);
    }

    // Returns a list of all the users songs
    @RequestMapping(value ="/api/user/{userId}/songs")
    public List<Song> getSongsByOwner (@PathVariable("userId") String userId)
    {
        return songRepository.findByOwnerId(userId);
    }

    // Adds a new user playlist
    @RequestMapping(value ="/api/add/playlist/")
    public String addPlaylist (@RequestParam("user_id") String user_id, @RequestParam("name") String newName)
    {
        // Add playlist to database
        // Uses a builder pattern
        playlistRepository.save(
                Playlist.builder()
                .name(newName)
                .ownerId(user_id)
                .build()
        );
        return "success";
    }

    // Adds a new song (just metadata about the song)
    @RequestMapping(value ="/api/add/song/")
    public String addSong (@RequestParam("title") String title, @RequestParam("artist") String artist, @RequestParam("filepath") String filepath, @RequestParam("ownerId") String ownerId)
    {
        // Prevent duplicate titles
        if (songRepository.findByOwnerIdAndTitle(ownerId, title) != null) return "duplicate";

        // Build a new song using builder pattern
        Song newSong = Song.builder()
                .title(title)
                .artist(artist)
                .filepath(filepath)
                .ownerId(ownerId)
                .build();

        // Add song to database
        songRepository.save(newSong);

        // Return the song ID
        return newSong.getSongId();
    }


}
