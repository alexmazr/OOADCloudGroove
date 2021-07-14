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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
public class User {

    @Builder.Default
    @Id
    String userId = UUID.randomUUID().toString();


    String email;
    String password;

    //List<Playlist> playlists;

}
