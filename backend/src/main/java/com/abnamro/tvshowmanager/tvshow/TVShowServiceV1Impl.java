package com.abnamro.tvshowmanager.tvshow;

import com.abnamro.tvshowmanager.common.exceptions.BadRequest;
import com.abnamro.tvshowmanager.common.exceptions.ResourceNotFound;
import com.abnamro.tvshowmanager.genre.Genre;
import com.abnamro.tvshowmanager.genre.GenreRepository;
import com.abnamro.tvshowmanager.tvshow.dto.TVShowDTO;
import com.abnamro.tvshowmanager.tvshow.mappers.TvShowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("V1")
public class TVShowServiceV1Impl implements TVShowService {

    private static final Logger logger = LoggerFactory.getLogger(TVShowServiceV1Impl.class);


    private final TVShowRepository tvShowRepository;
    private final GenreRepository genreRepository;


    public TVShowServiceV1Impl(TVShowRepository tvShowRepository, GenreRepository genreRepository) {
        this.tvShowRepository = tvShowRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Page<TVShow> getPage(int page, int size) {
        logger.debug("Fetching TV shows. Page: {}, Size: {}", page, size);
        if (page < 0 || size < 0) {
            throw new BadRequest("page number and size must not be less than one!");
        }
        Pageable pageable = PageRequest.of(page, size);
        return tvShowRepository.findAll(pageable);
    }

    @Override
    public Page<TVShow> getByGenre(String genre, int page, int size) {
        if (page < 0 || size < 0) {
            throw new BadRequest("page number and size must not be less than one!");
        }

        Pageable pageable = PageRequest.of(page, size);
        logger.debug("Searching for genre: {}", genre);


        // return an empty page if the genre does not exist. Maybe change it to throw a bad request?
        return genreRepository.findByNameIgnoreCase(genre)
                .map(genreObj -> tvShowRepository.findByGenre(genreObj, pageable))
                .orElse(Page.empty());
    }

    @Override
    public Optional<TVShow> getById(Long id) {
        return tvShowRepository.findById(id);
    }


    @Override
    public TVShow save(TVShowDTO tvShowDTO) {
        tvShowRepository.findByTitleIgnoreCase(tvShowDTO.title())
                .ifPresent(t -> {
                    throw new BadRequest(String.format("TV Show '%s' already exists.", tvShowDTO.title()));
                });
        Genre genre = genreRepository.findById(tvShowDTO.genreId())
                .orElseThrow(() -> new ResourceNotFound("Genre does not exist."));

        TVShow tvShow = TvShowMapper.toEntity(tvShowDTO, genre);

        return tvShowRepository.save(tvShow);
    }

    @Override
    public void update(Long id, TVShowDTO tvShowDTO) {
        var tvShow = getById(id).orElseThrow(() -> new ResourceNotFound("tv show not found"));

        Optional<TVShow> existingTVShow = tvShowRepository.findByTitleIgnoreCase(tvShowDTO.title());

        // If the title already exists, and it belongs to a different TVShow, throw an exception
        existingTVShow.ifPresent(existing -> {
            if (existing.getId() != id)
                throw new BadRequest(String.format("TV Show with title '%s' already exists.", existing.getTitle()));
        });


        Genre genre = genreRepository.findById(tvShowDTO.genreId())
                .orElseThrow(() -> new ResourceNotFound("Genre does not exist"));

        tvShow.setTitle(tvShowDTO.title());
        tvShow.setGenre(genre);
        tvShowRepository.save(tvShow);
    }

    @Override
    public void delete(Long id) {
        tvShowRepository.deleteById(id);
    }
}
