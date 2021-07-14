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

    @RequestMapping(value ="/api/song/{song_id}")
    public Song getSong (@PathVariable("song_id") String song_id)
    {
        return songRepository.findBySongId(song_id);
    }

    @RequestMapping(value ="/api/add/playlist/")
    public String addPlaylist (@RequestParam("user_id") String user_id, @RequestParam("name") String newName)
    {
        // Add playlist to database
        playlistRepository.save(
                Playlist.builder()
                .name(newName)
                .ownerId(user_id)
                .build()
        );
        return "success";
    }

    @RequestMapping(value ="/api/add/song/")
    public String addSong (@RequestParam("title") String title, @RequestParam("artist") String artist, @RequestParam("filepath") String filepath, @RequestParam("ownerId") String ownerId)
    {
        // Add song to database
        songRepository.save(
                Song.builder()
                        .title(title)
                        .artist(artist)
                        .filepath(filepath)
                        .ownerId(ownerId)
                        .build()
        );

        return "success";
    }


}
