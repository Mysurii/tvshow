package com.abnamro.tvshowmanager.genre;

import com.abnamro.tvshowmanager.common.dto.Response;
import com.abnamro.tvshowmanager.common.exceptions.ResourceNotFound;
import com.abnamro.tvshowmanager.genre.dto.GenreDTO;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/genres")
public class GenreControllerV1 {

    private final GenreServiceV1Impl genreService;

    @Autowired
    public GenreControllerV1(GenreServiceV1Impl genreService) {
        this.genreService = genreService;
    }

    @RateLimiter(name = "GlobalRateLimiterConfig")
    @GetMapping
    public Response<List<Genre>> getAllGenres() {
        List<Genre> genres = genreService.getAll();

        return Response.successResponse(genres);
    }

    @RateLimiter(name = "GlobalRateLimiterConfig")
    @GetMapping("{id}")
    public Response<Genre> getGenreById(@PathVariable Long id) {
        Genre genre = genreService.getById(id)
                .orElseThrow(() -> new ResourceNotFound(String.format("Genre with id %d not found", id)));

        return Response.successResponse(genre);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Genre> saveGenre(@Valid @RequestBody GenreDTO newGenre) {
        var createdGenre = genreService.save(newGenre);

        return Response.successResponse(createdGenre);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGenre(@PathVariable Long id, @Valid @RequestBody GenreDTO genreDto) {
        genreService.update(id, genreDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGenre(@PathVariable Long id) {
        genreService.delete(id);
    }
}
