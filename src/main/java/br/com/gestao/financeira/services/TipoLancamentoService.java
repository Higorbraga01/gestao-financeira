package br.com.gestao.financeira.services;

import br.com.gestao.financeira.models.TipoLancamento;
import br.com.gestao.financeira.repositories.TipoLancamentoRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoLancamentoService implements BaseService<TipoLancamento> {

    private final TipoLancamentoRepository repository;

    @Autowired
    public TipoLancamentoService(TipoLancamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public TipoLancamento create(TipoLancamento dto) {
        return repository.save(dto);
    }

    @Override
    public TipoLancamento update(Long id, TipoLancamento dto) {
        Optional<TipoLancamento> found = repository.findById(id);
        if(found.isPresent()){
            BeanUtils.copyProperties(dto, found.get());
           return repository.save(found.get());
        }
        return found.orElse(null);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<TipoLancamento> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate,pageable);
    }

    @Override
    public List<TipoLancamento> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TipoLancamento> findById(Long id) {
        return repository.findById(id);
    }
}
