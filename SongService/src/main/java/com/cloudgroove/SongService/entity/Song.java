package com.cloudgroove.SongService.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Song")
public class Song {

    @Builder.Default
    @Id
    String songId = UUID.randomUUID().toString();

    String title;
    String artist;
    String filepath;
    String ownerId;
}
