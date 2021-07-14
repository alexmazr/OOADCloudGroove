package com.cloudgroove.SongService.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlaylistWrapper {
    List<Playlist> playlists;
}
