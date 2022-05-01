package br.com.gestao.financeira.interceptors;

import br.com.gestao.financeira.http.response.UserResponse;
import br.com.gestao.financeira.models.User;
import br.com.gestao.financeira.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    final UserRepository userRepository;

    @Autowired
    public AuthInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws EntityNotFoundException {
        Object principal = null;

        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }

        if (principal != null && !principal.equals("anonymousUser")) {
            String userName = principal.toString();

            List<String> roles = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getAuthorities()
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
            User user = userRepository.findByUsername(userName);
            request.setAttribute("currentUser", new UserResponse(user.getId(), user.getName(), user.getUsername(), roles));
        }

        return true;
    }
}
