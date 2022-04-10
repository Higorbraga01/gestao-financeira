package br.com.gestao.financeira.services;

import br.com.gestao.financeira.http.request.LancamentoRequest;
import br.com.gestao.financeira.models.Lancamento;
import br.com.gestao.financeira.repositories.LancamentoRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService implements BaseService<Lancamento, LancamentoRequest> {

    private final LancamentoRepository repository;

    @Autowired
    public LancamentoService(LancamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Lancamento create(LancamentoRequest dto) {
        Lancamento lancamento = new Lancamento();
        BeanUtils.copyProperties(dto, lancamento);
        return repository.save(lancamento);
    }

    @Override
    public Lancamento update(Long id, LancamentoRequest dto) {
        Optional<Lancamento> found = repository.findById(id);
        if(found.isPresent()){
            BeanUtils.copyProperties(dto, found.get());
            return repository.save(found.get());
        }
        return found.orElse(null);
    }

    @Override
    public void delete(Long id) {
        Optional<Lancamento> found = repository.findById(id);
        found.ifPresent(repository::delete);
    }

    @Override
    public Page<Lancamento> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate,pageable);
    }

    @Override
    public List<Lancamento> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Lancamento> findById(Long id) {
        return repository.findById(id);
    }
}
