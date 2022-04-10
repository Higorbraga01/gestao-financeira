package br.com.gestao.financeira.services;

import br.com.gestao.financeira.http.request.TipoLancamentoRequest;
import br.com.gestao.financeira.models.Lancamento;
import br.com.gestao.financeira.models.TipoLancamento;
import br.com.gestao.financeira.repositories.TipoLancamentoRepository;
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
public class TipoLancamentoService implements BaseService<TipoLancamento, TipoLancamentoRequest> {

    private final TipoLancamentoRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public TipoLancamentoService(TipoLancamentoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TipoLancamento create(TipoLancamentoRequest dto) {
        return repository.save(modelMapper.map(dto, TipoLancamento.class));
    }

    @Override
    public TipoLancamento update(Long id, TipoLancamentoRequest dto) {
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
