package com.cloudgroove.SongService.repository;


import com.cloudgroove.SongService.entity.Playlist;
import com.cloudgroove.SongService.entity.PlaylistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String>
{
    Optional<List<Playlist>> findAllByOwnerId(String ownerId);
}
