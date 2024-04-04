package com.abnamro.tvshowmanager.genre;

import com.abnamro.tvshowmanager.common.interfaces.IRead;
import com.abnamro.tvshowmanager.common.interfaces.IWrite;
import com.abnamro.tvshowmanager.common.interfaces.ListableService;
import com.abnamro.tvshowmanager.genre.dto.GenreDTO;

public interface GenreService extends IRead<Genre>, IWrite<Genre, GenreDTO>, ListableService<Genre> {
}
