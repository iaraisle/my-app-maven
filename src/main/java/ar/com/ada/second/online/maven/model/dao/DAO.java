package ar.com.ada.second.online.maven.model.dao;

import java.util.Optional;

public interface DAO<T> {
    void save (T t);

    Integer getTotalRecords();

    Optional<T> findById(Integer id);
}
