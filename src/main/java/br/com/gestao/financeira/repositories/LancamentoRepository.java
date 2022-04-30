package br.com.gestao.financeira.repositories;

import br.com.gestao.financeira.models.Lancamento;
import br.com.gestao.financeira.models.QLancamento;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoRepository extends
        JpaRepository<Lancamento, Long>,
        QuerydslPredicateExecutor<Lancamento>,
        JpaSpecificationExecutor<Lancamento>,
        QuerydslBinderCustomizer<QLancamento> {

    Page<Lancamento> findAllByUser_Id(Long userId , Pageable pageable);

    @Override
    default void customize(QuerydslBindings bindings, QLancamento lancamento) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
