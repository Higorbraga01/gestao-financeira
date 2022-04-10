package br.com.gestao.financeira.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;

public interface BaseService<E, T> {
    E create(T dto);
    E update(Long id, T dto);
    void delete(Long id);
    Page<E> findAll(Predicate predicate, Pageable pageable);
    List<E> findAll();
    Optional<E> findById(Long id);
}
