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
@Table(name = "Playlist")
public class Playlist {

    @Builder.Default
    @Id
    String playlistId = UUID.randomUUID().toString();
    String name;
    String ownerId;
}
