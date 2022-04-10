package br.com.gestao.financeira.services;

import br.com.gestao.financeira.http.request.SubCategoriaRequest;
import br.com.gestao.financeira.models.SubCategoria;
import br.com.gestao.financeira.repositories.SubCategoriaRepository;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoriaService implements BaseService<SubCategoria, SubCategoriaRequest> {

    private final SubCategoriaRepository repository;
    private final ModelMapper modelMapper;
    @Autowired
    public SubCategoriaService(SubCategoriaRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SubCategoria create(SubCategoriaRequest dto) {
        SubCategoria subCategoria = new SubCategoria();
        return repository.save(modelMapper.map(dto, SubCategoria.class));
    }

    @Override
    public SubCategoria update(Long id, SubCategoriaRequest dto) {
        return null;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<SubCategoria> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }

    @Override
    public List<SubCategoria> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<SubCategoria> findById(Long id) {
        return repository.findById(id);
    }
}
