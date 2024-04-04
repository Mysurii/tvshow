package com.abnamro.tvshowmanager.tvshow;

import com.abnamro.tvshowmanager.common.dto.Response;
import com.abnamro.tvshowmanager.common.exceptions.ResourceNotFound;
import com.abnamro.tvshowmanager.tvshow.dto.TVShowDTO;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tv-shows")
public class TvShowControllerV1 {

    private final TVShowServiceV1Impl tvShowServiceImpl;

    @Autowired
    public TvShowControllerV1(TVShowServiceV1Impl tvShowServiceImpl) {
        this.tvShowServiceImpl = tvShowServiceImpl;
    }

    @RateLimiter(name = "GlobalRateLimiterConfig")
    @GetMapping
    public Response<Page<TVShow>> getAllTVShows(
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
            ) {

        Page<TVShow> tvShows = genre == null ?
                tvShowServiceImpl.getPage(page, size) : tvShowServiceImpl.getByGenre(genre, page, size);
        return Response.successResponse(tvShows);
    }

    @RateLimiter(name = "GlobalRateLimiterConfig")
    @GetMapping("{id}")
    public Response<TVShow> getTvShowById(@PathVariable Long id) {

        TVShow tvShow = tvShowServiceImpl.getById(id)
                .orElseThrow(() -> new ResourceNotFound(String.format("TV show with id %d not found", id)));

        return Response.successResponse(tvShow);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<TVShow> saveTvShow(@Valid @RequestBody TVShowDTO tvShowDTO) {
        TVShow createdTvShow = tvShowServiceImpl.save(tvShowDTO);

        return Response.successResponse(createdTvShow);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTvShow(@PathVariable Long id, @Valid @RequestBody TVShowDTO tvShowDTO) {
        tvShowServiceImpl.update(id, tvShowDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTvShow(@PathVariable Long id) {
        tvShowServiceImpl.delete(id);
    }
}
