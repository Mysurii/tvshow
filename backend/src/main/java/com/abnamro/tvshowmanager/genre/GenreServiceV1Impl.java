package com.abnamro.tvshowmanager.genre;


import com.abnamro.tvshowmanager.common.exceptions.BadRequest;
import com.abnamro.tvshowmanager.common.exceptions.ResourceNotFound;
import com.abnamro.tvshowmanager.genre.dto.GenreDTO;
import com.abnamro.tvshowmanager.genre.mappers.GenreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("v1")
public class GenreServiceV1Impl implements GenreService  {

    private static final Logger logger = LoggerFactory.getLogger(GenreServiceV1Impl.class);

    private final GenreRepository genreRepository;

    public GenreServiceV1Impl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> getById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public Genre save(GenreDTO dto) {
        logger.debug("Saving new genre: {}", dto.name());
        var genre = GenreMapper.toEntity(dto);
        var optionalGenre = genreRepository.findByNameIgnoreCase(genre.getName());
        if (optionalGenre.isPresent()) {
            throw new BadRequest("Genre already exists.");
        }

        return genreRepository.save(genre);
    }

    @Override
    public void delete(Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public void update(Long id, GenreDTO dto) {
        logger.debug("Updating genre with id: {}", id);
        var genre = this.getById(id).orElseThrow(() -> new ResourceNotFound("Genre not found"));

        var existingGenre = genreRepository.findByNameIgnoreCase(dto.name());

        existingGenre.ifPresent(existing -> {
            if (existing.getId() != id) {
                logger.debug("Failed to update genre. Genre '{}' already exists.", dto.name());
                throw new BadRequest(String.format("Genre '%s' already exists.", existing.getName()));
            }
        });

        genre.setName(dto.name());
        genreRepository.save(genre);
    }
}
