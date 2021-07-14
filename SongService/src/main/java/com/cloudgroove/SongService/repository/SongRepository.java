package com.cloudgroove.SongService.repository;


import com.cloudgroove.SongService.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, String> {
    Song findBySongId(String songId);
}
