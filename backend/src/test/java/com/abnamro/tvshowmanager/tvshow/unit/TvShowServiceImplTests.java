package com.abnamro.tvshowmanager.tvshow.unit;

import com.abnamro.tvshowmanager.common.exceptions.BadRequest;
import com.abnamro.tvshowmanager.common.exceptions.ResourceNotFound;
import com.abnamro.tvshowmanager.genre.Genre;
import com.abnamro.tvshowmanager.genre.GenreRepository;
import com.abnamro.tvshowmanager.tvshow.TVShow;
import com.abnamro.tvshowmanager.tvshow.TVShowRepository;
import com.abnamro.tvshowmanager.tvshow.TVShowServiceV1Impl;
import com.abnamro.tvshowmanager.tvshow.dto.TVShowDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TvShowServiceImplTests {

    @InjectMocks
    private TVShowServiceV1Impl tvShowServiceImpl;

    @Mock
    private TVShowRepository tvShowRepository;

    @Mock
    private GenreRepository genreRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_createTVShow_when_validArgsProvided() {
        // Given
        TVShowDTO dto = new TVShowDTO("New TV Show", 1L);
        TVShow expectedTvShow = new TVShow();

        when(tvShowRepository.findByTitleIgnoreCase("New TV Show")).thenReturn(Optional.empty());
        when(genreRepository.findById(anyLong())).thenReturn(Optional.of(new Genre()));
        when(tvShowRepository.save(any(TVShow.class))).thenReturn(expectedTvShow);

        // When
        TVShow saved = tvShowServiceImpl.save(dto);


        // Then
        assertNotNull(saved);
        assertEquals(expectedTvShow.getTitle(), saved.getTitle());
    }

    @Test
    void should_throwError_when_genreDoesNotExist() {
        // Given
        TVShowDTO dto = new TVShowDTO("New TV Show", 1L);
        TVShow expectedTvShow = new TVShow();

        when(tvShowRepository.findByTitleIgnoreCase("New TV Show")).thenReturn(Optional.empty());
        when(genreRepository.findById(anyLong())).thenReturn(Optional.of(new Genre()));
        when(tvShowRepository.save(any(TVShow.class))).thenReturn(expectedTvShow);

        // When
        TVShow saved = tvShowServiceImpl.save(dto);


        // Then
        assertNotNull(saved);
        assertEquals(expectedTvShow.getTitle(), saved.getTitle());
    }

    @Test
    void should_throwException_when_titleAlreadyExists() {
        // Given
        TVShowDTO dto = new TVShowDTO("TvShow", 1L);
        TVShow existingTvShow = new TVShow();
        existingTvShow.setTitle("TvShow");


        TVShow newTvShow = new TVShow();
        newTvShow.setTitle("TvShow");

        when(tvShowRepository.findByTitleIgnoreCase("TvShow")).thenReturn(Optional.of(existingTvShow));

        // When, Then
        BadRequest exception = assertThrows(BadRequest.class, () -> {
            tvShowServiceImpl.save(dto);
        });

        assertEquals("TV Show 'TvShow' already exists.", exception.getMessage());
        verify(tvShowRepository, never()).save(any());
    }

    @Test
    void should_throwException_when_SizeInValid() {
        // Given
        int invalidPage = -1;
        int invalidSize = -1;

        // When, Then
        var exception = assertThrows(BadRequest.class, () -> {
            tvShowServiceImpl.getPage(invalidPage, invalidSize);
        });

        assertEquals("page number and size must not be less than one!", exception.getMessage());
    }

    @Test
    void should_returnPageOfTvShows_whenValidArgsProvided() {
        // Given
        int size = 3;
        int page = 0;

        Pageable pageable = PageRequest.of(page, size);

        Page<TVShow> expectedPage = mock(Page.class);
        when(tvShowRepository.findAll(pageable)).thenReturn(expectedPage);

        // When
        Page<TVShow> result = tvShowServiceImpl.getPage(page, size);

        // Then
        assertEquals(expectedPage, result);
    }

    @Test
    void should_returnDifferentResults_when_havingDifferentPages() {
        // Given
        Page<TVShow> page1Data = mock(Page.class);
        Page<TVShow> page2Data = mock(Page.class);

        when(tvShowRepository.findAll(PageRequest.of(0, 5))).thenReturn(page1Data);
        when(tvShowRepository.findAll(PageRequest.of(1, 5))).thenReturn(page2Data);

        // When
        Page<TVShow> resultPage1 = tvShowServiceImpl.getPage(0, 5);
        Page<TVShow> resultPage2 = tvShowServiceImpl.getPage(1, 5);

        // Then
        assertNotEquals(resultPage1, resultPage2);
    }


    @Test
    void should_throwException_whenSizeNotValid() {
        // Given
        int invalidPage = -1;
        int invalidSize = -1;


        // When, Then
        var exception = assertThrows(BadRequest.class, () -> {
            tvShowServiceImpl.getByGenre("genre", invalidPage, -invalidSize);
        });

        assertEquals("page number and size must not be less than one!", exception.getMessage());
    }

    @Test
    void should_returnEmptyPage_when_genreDoesNotExist() {
        // Given
        String nonExistentGenre = "action";

        when(genreRepository.findByNameIgnoreCase(nonExistentGenre)).thenReturn(Optional.empty());

        // When
        Page<TVShow> result = tvShowServiceImpl.getByGenre(nonExistentGenre, 0, 5);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void should_returnPageOfTVShows_whenValidParamsProvided() {
        // Given
        String genre = "comedy";
        int page = 0;
        int size = 5;

        Pageable pageable = PageRequest.of(page, size);
        Page<TVShow> expectedPage = mock(Page.class);
        when(tvShowRepository.findByGenre(any(), eq(pageable))).thenReturn(expectedPage);
        when(genreRepository.findByNameIgnoreCase(eq(genre))).thenReturn(Optional.of(new Genre(genre)));

        // When
        Page<TVShow> result = tvShowServiceImpl.getByGenre(genre, page, size);

        // Then
        assertEquals(expectedPage, result);
    }

    @Test
    void should_updateTVShow_when_ValidInputProvided() {
        // Given
        Long id = 1L;
        Long genreId = 3L;
        var genre = new Genre();

        TVShowDTO tvShowDTO = new TVShowDTO("New Title", genreId);

        TVShow existingTVShow = new TVShow("Old Title", genre);
        when(tvShowRepository.findById(id)).thenReturn(Optional.of(existingTVShow));

        TVShow updatedTVShow = new TVShow("New Title", genre);
        when(tvShowRepository.findByTitleIgnoreCase("New Title")).thenReturn(Optional.empty());
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));

        // When, then
        assertDoesNotThrow(() -> tvShowServiceImpl.update(id, tvShowDTO));

        verify(tvShowRepository, times(1)).save(updatedTVShow);
    }

    @Test
    void should_throwException_whenTVShowNotFound() {
        // Given
        Long id = 1L;
        TVShowDTO tvShowDTO = new TVShowDTO("New Title", 2L);

        when(tvShowRepository.findById(id)).thenReturn(Optional.empty());

        // When, Then
        var exception = assertThrows(ResourceNotFound.class, () -> tvShowServiceImpl.update(id, tvShowDTO));
        assertEquals("tv show not found", exception.getMessage());
    }

    @Test
    void Should_throwException_when_titleAlreadyExists() {
        // Given
        Long id = 1L;
        Genre genre = new Genre();



        TVShow existingTVShow = new TVShow("Existing Title", genre);
        existingTVShow.setId(2L);
        TVShow toUpdate = new TVShow("New Title", genre);
        toUpdate.setId(id);

        TVShowDTO tvShowDTO = new TVShowDTO(toUpdate.getTitle(), toUpdate.getId());

        when(tvShowRepository.findById(id)).thenReturn(Optional.of(toUpdate));
        when(tvShowRepository.findByTitleIgnoreCase("New Title")).thenReturn(Optional.of(existingTVShow));


        // When, Then
        var exception = assertThrows(BadRequest.class, () -> tvShowServiceImpl.update(id, tvShowDTO));
        assertEquals("TV Show with title 'Existing Title' already exists.", exception.getMessage());
    }

    @Test
    void should_ThrowException_whenGenreNotFound() {
        // Given
        Long id = 1L;
        Genre genre = new Genre();
        TVShowDTO tvShowDTO = new TVShowDTO("New Title", id);
        TVShow existingTVShow = new TVShow("Existing Title", genre);
        existingTVShow.setId(id);

        when(tvShowRepository.findById(id)).thenReturn(Optional.of(existingTVShow));
        when(tvShowRepository.findByTitleIgnoreCase("Existing Title")).thenReturn(Optional.of(existingTVShow));


        // When, Then
        var exception = assertThrows(ResourceNotFound.class, () -> tvShowServiceImpl.update(id, tvShowDTO));
        assertEquals("Genre does not exist", exception.getMessage());
    }

    @Test
    void should_DeleteTVShow() {
        // Given
        Long id = 1L;

        // When
        tvShowServiceImpl.delete(id);

        // Then
        verify(tvShowRepository, times(1)).deleteById(id);
    }
}
