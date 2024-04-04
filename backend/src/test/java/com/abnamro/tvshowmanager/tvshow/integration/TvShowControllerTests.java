package com.abnamro.tvshowmanager.tvshow.integration;

import com.abnamro.tvshowmanager.common.exceptions.BadRequest;
import com.abnamro.tvshowmanager.common.exceptions.ResourceNotFound;
import com.abnamro.tvshowmanager.genre.Genre;
import com.abnamro.tvshowmanager.genre.GenreServiceV1Impl;
import com.abnamro.tvshowmanager.tvshow.TVShow;
import com.abnamro.tvshowmanager.tvshow.TVShowServiceV1Impl;
import com.abnamro.tvshowmanager.tvshow.TvShowControllerV1;
import com.abnamro.tvshowmanager.tvshow.dto.TVShowDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TvShowControllerV1.class)
public class TvShowControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TVShowServiceV1Impl tvShowService;

    @MockBean
    private GenreServiceV1Impl genreServiceV1;


    @Test
    void should_create_tvShow() throws Exception {
        // Given
        Genre genre = new Genre("Action");
        genre.setId(1L);
        when(genreServiceV1.getById(1L)).thenReturn(Optional.of(genre));

        TVShowDTO createShowDTO = new TVShowDTO("New TV Show", 1L);

        // When, Then
        mockMvc.perform(post("/v1/tv-shows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createShowDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void should_returnError_when_genreDoesNotExist() throws Exception {
        // Given
        TVShowDTO createShowDTO = new TVShowDTO("New TV Show", 99L);

        when(tvShowService.save(any(TVShowDTO.class)))
                .thenThrow(new ResourceNotFound("Genre does not exist"));


        // When, Then
        mockMvc.perform(post("/v1/tv-shows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createShowDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Genre does not exist"));
    }

    @Test
    void should_returnEmptyPage_when_noArgsProvided() throws Exception {
        Page<TVShow> tvShows = new PageImpl<>(List.of());
        when(tvShowService.getPage(0, 5)).thenReturn(tvShows);


        mockMvc.perform(get("/v1/tv-shows"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content").exists())
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content", hasSize(0)))
                .andExpect(jsonPath("$.data.totalPages").exists());

    }

    @Test
    void should_returnPage_when_validArgsProvided() throws Exception {
        Genre genre = new Genre("Action");
        Page<TVShow> tvShows = new PageImpl<>(List.of(
                new TVShow("show 1", genre),
                new TVShow("Show 2", genre)
        ));
        when(tvShowService.getPage(0, 5)).thenReturn(tvShows);


        mockMvc.perform(get("/v1/tv-shows"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content").exists())
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content", hasSize(2)));
    }

    @Test
    void should_returnTvshows_when_filteredByGenre() throws Exception {
        Genre genre1 = new Genre("Action");
        Genre genre2 = new Genre("Comedy");

        List<TVShow> tvshows = List.of(
                new TVShow("show 1", genre1),
                new TVShow("Show 2", genre2),
                new TVShow("Show 2", genre1)
        );

        Page<TVShow> filteredTvShows = new PageImpl<>(
                tvshows.stream().filter(tvShow ->
                        tvShow.getGenre().getName().equalsIgnoreCase("action")).collect(Collectors.toList())
        );

        when(tvShowService.getByGenre("action", 0, 5))
                .thenReturn(filteredTvShows);


        mockMvc.perform(get("/v1/tv-shows?genre=action"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content").exists())
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content", hasSize(filteredTvShows.getSize())));
    }


    @Test
    void should_returnEmptyPage_when_invalidGenre() throws Exception {
        when(tvShowService.getByGenre("fake-genre", 0, 5))
                .thenReturn(new PageImpl<>(List.of()));


        mockMvc.perform(get("/v1/tv-shows?genre=fake-genre"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content").exists())
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content", hasSize(0)));
    }

    @Test
    void should_throwError_when_invalidSize() throws Exception {
        when(tvShowService.getPage(-1, -1))
                .thenThrow(BadRequest.class);

        mockMvc.perform(get("/v1/tv-shows?size=-1&page=-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data.content").doesNotExist());
    }

    @Test
    void should_returnTVShow_when_idProvided() throws Exception {
        TVShow tvShow = new TVShow("Test Show", new Genre("Test Genre"));
        when(tvShowService.getById(1L)).thenReturn(Optional.of(tvShow));


        mockMvc.perform(get("/v1/tv-shows/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.title").value("Test Show"))
                .andExpect(jsonPath("$.data.genre.name").value("Test Genre"));
    }

    @Test
    void should_throwException_when_invalidTvshowId() throws Exception {
        when(tvShowService.getById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/tv-shows/15"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("TV show with id 15 not found"));
    }


    @Test
    void should_update_tvshow() throws Exception {
        TVShowDTO tvShowDTO = new TVShowDTO("Title", 1L);


        // Performing the PUT request to update a TV show with ID 1
        mockMvc.perform(put("/v1/tv-shows/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tvShowDTO)))
                .andExpect(status().isNoContent());


        verify(tvShowService).update(eq(1L), any(TVShowDTO.class));
    }

    @Test
    void should_throwException_when_tvShowNotExist() throws Exception {
        TVShowDTO tvShowDTO = new TVShowDTO("Title", 1L);


        // Performing the PUT request to update a TV show with ID 1
        mockMvc.perform(put("/v1/tv-shows/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tvShowDTO)))
                .andExpect(status().isNoContent());


        verify(tvShowService).update(eq(1L), any(TVShowDTO.class));
    }

    @Test
    void should_delete_tvShow() throws Exception {
        doNothing().when(tvShowService).delete(anyLong());

        mockMvc.perform(delete("/v1/tv-shows/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(tvShowService).delete(eq(1L));
    }





}
