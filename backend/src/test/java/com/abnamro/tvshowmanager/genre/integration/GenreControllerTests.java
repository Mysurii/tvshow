package com.abnamro.tvshowmanager.genre.integration;

import com.abnamro.tvshowmanager.common.exceptions.BadRequest;
import com.abnamro.tvshowmanager.genre.Genre;
import com.abnamro.tvshowmanager.genre.GenreControllerV1;
import com.abnamro.tvshowmanager.genre.GenreServiceV1Impl;
import com.abnamro.tvshowmanager.genre.dto.GenreDTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreControllerV1.class)
public class GenreControllerTests {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreServiceV1Impl genreServiceV1;


    @Test
    void should_createGenre() throws Exception {
        // Given
        GenreDTO genreDTO = new GenreDTO("New Genre");

        when(genreServiceV1.save(any(GenreDTO.class))).thenReturn(new Genre("New Genre"));


        // When, Then
        mockMvc.perform(post("/v1/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value(genreDTO.name()));
    }

    @Test
    void should_throwException_when_genreAlreadyExists() throws Exception {
        // Given
        GenreDTO genreDTO = new GenreDTO("New Genre");

        when(genreServiceV1.save(any(GenreDTO.class))).thenThrow(new BadRequest("Genre already exists."));


        // When, Then
        mockMvc.perform(post("/v1/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Genre already exists."));

    }

    @Test
    void should_returnAllGenres() throws Exception {
        // Given
        List<Genre> genres = List.of(
                new Genre("Genre1"),
                new Genre("Genre2")
        );

        when(genreServiceV1.getAll()).thenReturn(genres);

        // When, Then
        mockMvc.perform(get("/v1/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].name").value("Genre1"))
                .andExpect(jsonPath("$.data[1].name").value("Genre2"));
    }

    @Test
    void should_returnGenreById_whenExists() throws Exception {
        // Given
        Long genreId = 1L;
        Genre genre = new Genre("Action");
        genre.setId(genreId);
        when(genreServiceV1.getById(genreId)).thenReturn(Optional.of(genre));

        // When, Then
        mockMvc.perform(get("/v1/genres/{id}", genreId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Action"));
    }

    @Test
    void should_returnException_when_genreNotFound() throws Exception {
        // Given
        long genreId = 99L;
        when(genreServiceV1.getById(anyLong())).thenReturn(Optional.empty());

        // When, Then
        mockMvc.perform(get("/v1/genres/{id}", genreId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value(String.format("Genre with id %d not found", genreId)));
    }

    @Test
    void should_updateGenre_whenValidDataProvided() throws Exception {
        // Given
        GenreDTO updateGenreDTO = new GenreDTO("Updated Genre");

        // When, Then
        mockMvc.perform(put("/v1/genres/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateGenreDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_deleteGenre_whenValidIdProvided() throws Exception {
        // Given
        Long genreId = 1L;

        // When, Then
        mockMvc.perform(delete("/v1/genres/{id}", genreId))
                .andExpect(status().isNoContent());
    }
}
