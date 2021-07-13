package com.cloudgroove.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    @JsonProperty("title")
    String title;
    @JsonProperty("artist")
    String artist;
    @JsonProperty("filepath")
    String filepath;
    @JsonProperty("songId")
    String songId;
    @JsonProperty("ownerId")
    String ownerId;

}