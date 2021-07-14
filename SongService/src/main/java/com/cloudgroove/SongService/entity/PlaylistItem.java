package com.cloudgroove.SongService.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "PlaylistItem")
public class PlaylistItem {

    @Builder.Default
    @Id
    String playlistItemId = UUID.randomUUID().toString();

    String playlistId;
    String songId;
    String userId;
}
