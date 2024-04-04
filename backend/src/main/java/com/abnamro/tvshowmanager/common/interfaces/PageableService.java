package com.abnamro.tvshowmanager.common.interfaces;

import org.springframework.data.domain.Page;

public interface PageableService<T> {
    Page<T> getPage(int page, int size);
}
