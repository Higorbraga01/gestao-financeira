package br.com.gestao.financeira.services;

import br.com.gestao.financeira.http.request.ParcelaLancamentoRequest;
import br.com.gestao.financeira.models.ParcelaLancamento;
import br.com.gestao.financeira.repositories.ParcelaLancamentoRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParcelaLancamentoService implements BaseService<ParcelaLancamento, ParcelaLancamentoRequest> {

    private final ParcelaLancamentoRepository repository;

    @Autowired
    public ParcelaLancamentoService(ParcelaLancamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ParcelaLancamento create(ParcelaLancamentoRequest dto) {
        return null;
    }

    @Override
    public ParcelaLancamento update(Long id, ParcelaLancamentoRequest dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Page<ParcelaLancamento> findAll(Predicate predicate, Pageable pageable) {
        return null;
    }

    @Override
    public List<ParcelaLancamento> findAll() {
        return null;
    }

    @Override
    public Optional<ParcelaLancamento> findById(Long id) {
        return Optional.empty();
    }
}
