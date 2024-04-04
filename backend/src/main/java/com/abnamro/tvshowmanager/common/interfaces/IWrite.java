package com.abnamro.tvshowmanager.common.interfaces;

public interface IWrite<E, D> {
    E save(D dto);
    void update(Long id, D dto);
    void delete(Long id);
}
