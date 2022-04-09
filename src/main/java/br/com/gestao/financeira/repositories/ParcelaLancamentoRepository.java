
package br.com.gestao.financeira.repositories;

import br.com.gestao.financeira.models.ParcelaLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelaLancamentoRepository extends JpaRepository<ParcelaLancamento, Long>, QuerydslPredicateExecutor<ParcelaLancamento>, JpaSpecificationExecutor<ParcelaLancamento> {
}
