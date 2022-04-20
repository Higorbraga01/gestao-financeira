package br.com.gestao.financeira.services;

import br.com.gestao.financeira.http.request.LancamentoRequest;
import br.com.gestao.financeira.models.Lancamento;
import br.com.gestao.financeira.models.ParcelaLancamento;
import br.com.gestao.financeira.repositories.LancamentoRepository;
import br.com.gestao.financeira.repositories.ParcelaLancamentoRepository;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LancamentoService implements BaseService<Lancamento, LancamentoRequest> {

    private final ModelMapper modelMapper;
    private final LancamentoRepository repository;
    private final ParcelaLancamentoRepository parcelaLancamentoRepository;

    @Autowired
    public LancamentoService(ModelMapper modelMapper, LancamentoRepository repository, ParcelaLancamentoRepository parcelaLancamentoRepository) {
        this.modelMapper = modelMapper;
        this.repository = repository;
        this.parcelaLancamentoRepository = parcelaLancamentoRepository;
    }

    @Override
    public Lancamento create(LancamentoRequest dto) {
        Lancamento lancamento = modelMapper.map(dto, Lancamento.class);
        lancamento = repository.saveAndFlush(lancamento);
        if(lancamento.getQuantidadeRepeticao() > 0 && lancamento.getQuantidadeRepeticao() !=null){
            generatorInstallments(lancamento);
        }
        return lancamento;
    }

    private void generatorInstallments(Lancamento lancamento) {
        BigInteger valorParcela = lancamento.getValorTotal().divide(new BigInteger(String.valueOf(lancamento.getQuantidadeRepeticao())));
        for (int i = 0; lancamento.getQuantidadeRepeticao() > i ; i++){
            ParcelaLancamento parcela = new ParcelaLancamento();
            parcela.setLancamento(lancamento);
            parcela.setData(lancamento.getDataCriacao().plusMonths(i+1));
            parcela.setValor(valorParcela);
            parcela.setDataCriacao(LocalDateTime.now());
            parcela.setNumero(i+1);
            parcelaLancamentoRepository.saveAndFlush(parcela);
        }
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
