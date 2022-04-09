package br.com.gestao.financeira.repositories;

import br.com.gestao.financeira.models.QUsuario;
import br.com.gestao.financeira.models.Usuario;
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
public interface UsuarioRepository extends
        JpaRepository<Usuario, Long>,
        QuerydslPredicateExecutor<Usuario>,
        JpaSpecificationExecutor<Usuario>,
        QuerydslBinderCustomizer<QUsuario> {

    @Override
    default void customize(QuerydslBindings bindings, QUsuario usuario) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
