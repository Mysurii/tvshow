package com.abnamro.tvshowmanager.tvshow;

import com.abnamro.tvshowmanager.genre.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TVShowRepository extends JpaRepository<TVShow, Long> {
    Optional<TVShow> findByTitleIgnoreCase(String name);
    Page<TVShow> findByGenre(Genre genre, Pageable pageable);

    @Override
    Page<TVShow> findAll(Pageable pageable);
}
