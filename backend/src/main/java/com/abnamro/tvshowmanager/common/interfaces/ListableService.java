package com.abnamro.tvshowmanager.common.interfaces;

import java.util.List;

public interface ListableService <T> {
    List<T> getAll();
}
