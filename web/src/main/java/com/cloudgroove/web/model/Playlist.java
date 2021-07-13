package com.cloudgroove.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {
    @JsonProperty("name")
    String name;
    @JsonProperty("playlistId")
    String playlistId;
    @JsonProperty("ownerId")
    String ownerId;


}