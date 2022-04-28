package br.com.gestao.financeira.interceptors;

import br.com.gestao.financeira.http.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws EntityNotFoundException, IOException {
        Object principal = null;

        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        log.info("chamando interceptor {}", principal);
        if (principal != null && principal.getClass() != String.class && !principal.equals("anonymousUser")) {
            String keycloakUsername = principal.toString();

            List<String> roles = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getAuthorities()
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());

//            PessoaDTO pessoa = repository.findById(keycloakUsername).orElse(null);

            request.setAttribute("currentUser", UserResponse.builder().roles(roles).username(keycloakUsername));
        }

        return true;
    }
}
