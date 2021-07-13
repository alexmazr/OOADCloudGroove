package com.cloudgroove.web.model;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class PlaylistItem {
    String playlistId;
    String songId;
    String userId;
}