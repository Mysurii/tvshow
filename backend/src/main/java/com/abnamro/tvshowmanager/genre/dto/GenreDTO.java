package com.abnamro.tvshowmanager.genre.dto;

import jakarta.validation.constraints.NotBlank;

public record GenreDTO(@NotBlank(message = "Name is required") String name) { }
