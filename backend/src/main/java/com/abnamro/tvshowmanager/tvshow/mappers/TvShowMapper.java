package com.abnamro.tvshowmanager.tvshow.mappers;

import com.abnamro.tvshowmanager.genre.Genre;
import com.abnamro.tvshowmanager.tvshow.TVShow;
import com.abnamro.tvshowmanager.tvshow.dto.TVShowDTO;

public class TvShowMapper {
    public static TVShowDTO toDTO(TVShow tvShow) {
        return new TVShowDTO(tvShow.getTitle(), tvShow.getGenre().getId());
    }

    public static TVShow toEntity(TVShowDTO dto, Genre genre) {
        return new TVShow(dto.title(), genre);
    }
}
