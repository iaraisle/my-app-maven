package ar.com.ada.second.online.maven.model.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Optional;

public interface DAO<T> {
    void save (T t);

    Integer getTotalRecords();

    Optional<T> findById(Integer id);

    Boolean delete(T t);
}
