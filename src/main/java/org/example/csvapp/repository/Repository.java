package org.example.csvapp.repository;

import java.util.List;

/**
 * Generic repository interface for CRUD operations.
 *
 * @param <T>  entity type
 */
public interface Repository<T> {

    List<T> findAll();
}
