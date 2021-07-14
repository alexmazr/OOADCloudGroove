package com.cloudgroove.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlaylistItemWrapper {
    @JsonProperty("playlists")
    List<PlaylistItem> playlistItems;
}
