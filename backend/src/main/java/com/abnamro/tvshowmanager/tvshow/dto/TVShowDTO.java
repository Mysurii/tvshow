package com.abnamro.tvshowmanager.tvshow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TVShowDTO(
        @NotBlank(message = "Title is required") String title,
        @NotNull(message = "GenreId is required") Long genreId) {
}
