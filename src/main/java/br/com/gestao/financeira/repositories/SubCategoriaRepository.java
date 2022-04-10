package br.com.gestao.financeira.repositories;

import br.com.gestao.financeira.models.QSubCategoria;
import br.com.gestao.financeira.models.SubCategoria;
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
public interface SubCategoriaRepository extends
        JpaRepository<SubCategoria, Long>,
        QuerydslPredicateExecutor<SubCategoria>,
        JpaSpecificationExecutor<SubCategoria>,
        QuerydslBinderCustomizer<QSubCategoria> {

    @Override
    default void customize(QuerydslBindings bindings, QSubCategoria subCategoria) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
