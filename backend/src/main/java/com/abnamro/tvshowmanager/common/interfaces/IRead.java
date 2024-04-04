package com.abnamro.tvshowmanager.common.interfaces;

import java.util.Optional;

public interface IRead<T> {
    Optional<T> getById(Long id);
}
