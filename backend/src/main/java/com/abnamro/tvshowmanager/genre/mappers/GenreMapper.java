package com.abnamro.tvshowmanager.genre.mappers;

import com.abnamro.tvshowmanager.genre.Genre;
import com.abnamro.tvshowmanager.genre.dto.GenreDTO;


public class GenreMapper {
    public static GenreDTO toDTO(Genre genre) {
        return new GenreDTO(genre.getName());
    }

    public static Genre toEntity(GenreDTO genreDTO) {
        return new Genre(genreDTO.name());
    }


}
