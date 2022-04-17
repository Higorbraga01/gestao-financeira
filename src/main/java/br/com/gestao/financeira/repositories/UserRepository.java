package br.com.gestao.financeira.repositories;

import br.com.gestao.financeira.models.QUser;
import br.com.gestao.financeira.models.User;
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
public interface UserRepository extends
        JpaRepository<User, Long>,
        QuerydslPredicateExecutor<User>,
        JpaSpecificationExecutor<User>,
        QuerydslBinderCustomizer<QUser> {

    User findByUsername(String userName);

    @Override
    default void customize(QuerydslBindings bindings, QUser user) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
