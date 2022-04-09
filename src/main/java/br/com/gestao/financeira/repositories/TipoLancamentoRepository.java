package br.com.gestao.financeira.repositories;

import br.com.gestao.financeira.models.TipoLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoLancamentoRepository extends JpaRepository<TipoLancamento, Long>, QuerydslPredicateExecutor<TipoLancamento>, JpaSpecificationExecutor<TipoLancamento> {
}
