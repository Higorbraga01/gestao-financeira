package br.com.gestao.financeira.repositories;

import br.com.gestao.financeira.models.QLancamento;
import br.com.gestao.financeira.models.QTipoLancamento;
import br.com.gestao.financeira.models.TipoLancamento;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoLancamentoRepository extends
        JpaRepository<TipoLancamento, Long>,
        QuerydslPredicateExecutor<TipoLancamento>,
        JpaSpecificationExecutor<TipoLancamento>,
        QuerydslBinderCustomizer<QTipoLancamento> {

    @Override
    default void customize(QuerydslBindings bindings, QTipoLancamento tipoLancamento) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
