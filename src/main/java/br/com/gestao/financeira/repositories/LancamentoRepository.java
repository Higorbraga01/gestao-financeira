package br.com.gestao.financeira.repositories;

import br.com.gestao.financeira.models.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, QuerydslPredicateExecutor<Lancamento>, JpaSpecificationExecutor<Lancamento> {
}
