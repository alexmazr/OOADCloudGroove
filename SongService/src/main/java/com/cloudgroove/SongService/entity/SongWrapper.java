package com.cloudgroove.SongService.entity;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SongWrapper {
    List<Song> songs;
}
