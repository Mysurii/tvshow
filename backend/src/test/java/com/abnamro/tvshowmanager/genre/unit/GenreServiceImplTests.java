package com.abnamro.tvshowmanager.genre.unit;

import com.abnamro.tvshowmanager.common.exceptions.BadRequest;
import com.abnamro.tvshowmanager.genre.Genre;
import com.abnamro.tvshowmanager.genre.GenreRepository;
import com.abnamro.tvshowmanager.genre.GenreServiceV1Impl;
import com.abnamro.tvshowmanager.genre.dto.GenreDTO;
import com.abnamro.tvshowmanager.genre.mappers.GenreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GenreServiceImplTests {

    @InjectMocks
    private GenreServiceV1Impl genreService;

    @Mock
    private GenreRepository genreRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_returnGenre_when_validArgsProvided() {
        var genreDto = new GenreDTO("action");
        Genre expectedGenre = new Genre( "action");
        expectedGenre.setId(1L);


        when(genreRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(genreRepository.save(any(Genre.class))).thenReturn(expectedGenre);

        // When
        Genre saved = genreService.save(genreDto);


        // Then
        assertEquals(expectedGenre.getId(), saved.getId());
        assertEquals(expectedGenre.getName(), saved.getName());
    }

    @Test
    void should_throwException_when_genreExists() {
        var genreDto = new GenreDTO("action");

        when(genreRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.of(GenreMapper.toEntity(genreDto)));

        // When, Then
        BadRequest exception = assertThrows(BadRequest.class, () -> genreService.save(genreDto));

        assertEquals("Genre already exists.", exception.getMessage());
        verify(genreRepository, never()).save(any());
    }


    @Test
    void should_return_allGenres_when_findAll() {
        // Given
        var genres = List.of(
                new Genre("Genre 1"),
                new Genre("Genre 2")
        );


        when(genreRepository.findAll()).thenReturn(genres);

        // When
        List<Genre> result = genreService.getAll();

        // Then
        assertEquals(genres, result);
    }


    @Test
    void should_updateGenre_whenValidInputProvided() {
        // Given
        Long id = 1L;

        var genre = new Genre("Existing Genre");
        var genreDTO = new GenreDTO("New genre");

        GenreDTO createGenreDTO = new GenreDTO("New genre");


        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
        when(genreRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.empty());


        // When, then
        assertDoesNotThrow(() -> genreService.update(id, genreDTO));

        verify(genreRepository, times(1)).save(any(Genre.class));
    }

    @Test
    void should_deleteGenre_when_idProvided() {
        // Given
        Long id = 1L;

        // When
        genreService.delete(id);

        // Then
        verify(genreRepository, times(1)).deleteById(id);
    }


}
