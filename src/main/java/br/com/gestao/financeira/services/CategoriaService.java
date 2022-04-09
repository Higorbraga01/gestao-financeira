package br.com.gestao.financeira.services;

import br.com.gestao.financeira.models.Categoria;
import br.com.gestao.financeira.repositories.CategoriaRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService implements BaseService<Categoria>{

    private final CategoriaRepository repository;

    @Autowired
    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Categoria create(Categoria dto) {
        Categoria categoria = new Categoria();
        BeanUtils.copyProperties(dto, categoria);
        return repository.save(categoria);
    }

    @Override
    public Categoria update(Long id, Categoria dto) {
        Optional<Categoria> found = repository.findById(id);
        found.ifPresent(categoria -> {
            BeanUtils.copyProperties(dto, categoria);
            repository.save(categoria);
        });
        return found.orElse(null);
    }

    @Override
    public void delete(Long id) {
        Optional<Categoria> found = repository.findById(id);
        found.ifPresent(repository::delete);
    }

    @Override
    public Page<Categoria> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }

    @Override
    public List<Categoria> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Categoria> findById(Long id) {
        return repository.findById(id);
    }
}
