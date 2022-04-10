package br.com.gestao.financeira.services;

import br.com.gestao.financeira.http.request.CategoriaRequest;
import br.com.gestao.financeira.models.Categoria;
import br.com.gestao.financeira.repositories.CategoriaRepository;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService implements BaseService<Categoria, CategoriaRequest>{

    private final CategoriaRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoriaService(CategoriaRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Categoria create(CategoriaRequest dto) {
        Categoria categoria = new Categoria();
        modelMapper.map(dto, categoria);
//        if(dto.getParentId() != null){
//            Optional<Categoria> found = repository.findById(dto.getParentId());
//            if(found.isPresent()){
//                categoria.setCategoria(found.get());
//            }
//        }
        return repository.save(categoria);
    }

    @Override
    public Categoria update(Long id, CategoriaRequest dto) {
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
