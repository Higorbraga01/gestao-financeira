package br.com.gestao.financeira.repositories;

import br.com.gestao.financeira.models.Categoria;
import br.com.gestao.financeira.models.QCategoria;
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
public interface CategoriaRepository extends
        JpaRepository<Categoria, Long>,
        QuerydslPredicateExecutor<Categoria>,
        JpaSpecificationExecutor<Categoria>,
        QuerydslBinderCustomizer<QCategoria> {

    @Override
    default void customize(QuerydslBindings bindings, QCategoria categoria) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    Page<Categoria> findAllByUser_Id(Long userId, Pageable pageable);
}
