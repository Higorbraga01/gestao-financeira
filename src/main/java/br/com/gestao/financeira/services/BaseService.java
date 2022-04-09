package br.com.gestao.financeira.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    T create(T dto);
    T update(Long id, T dto);
    void delete(Long id);
    Page<T> findAll(Predicate predicate, Pageable pageable);
    List<T> findAll();
    Optional<T> findById(Long id);
}
