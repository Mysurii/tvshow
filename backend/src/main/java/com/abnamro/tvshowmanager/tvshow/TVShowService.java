package com.abnamro.tvshowmanager.tvshow;

import com.abnamro.tvshowmanager.common.interfaces.IRead;
import com.abnamro.tvshowmanager.common.interfaces.IWrite;
import com.abnamro.tvshowmanager.common.interfaces.PageableService;
import com.abnamro.tvshowmanager.tvshow.dto.TVShowDTO;
import org.springframework.data.domain.Page;

public interface TVShowService extends IRead<TVShow>, IWrite<TVShow, TVShowDTO>, PageableService<TVShow> {
    Page<TVShow> getByGenre(String genre, int page, int size);
}
