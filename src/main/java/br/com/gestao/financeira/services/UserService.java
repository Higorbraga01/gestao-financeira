package br.com.gestao.financeira.services;

import br.com.gestao.financeira.http.request.UserRequest;
import br.com.gestao.financeira.models.Role;
import br.com.gestao.financeira.models.User;
import br.com.gestao.financeira.repositories.RoleRepository;
import br.com.gestao.financeira.repositories.UserRepository;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserService implements BaseService<User, UserRequest> {

    private final UserRepository repository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User create(UserRequest dto) {
        log.info("Saving new user {} to the database", dto.getName());
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return repository.save(user);
    }

    public Role saveRole(Role role) {
        log.info("Saving new  {} to the database", role.getName());
        return roleRepository.save(role);
    }

    public void addRoleToUser(String userName, String roleName) {
        log.info("Adding role {} to user {}", roleName, userName);
        User user = repository.findByUsername(userName);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    public  User getUser(String userName) {
        log.info("Fetching user {}", userName);
        return repository.findByUsername(userName);
    }


    @Override
    public User update(Long id, UserRequest dto) {
        Optional<User> found = repository.findById(id);
        found.ifPresent(usuario -> {
            BeanUtils.copyProperties(dto, usuario);
            repository.save(usuario);
        });
        return found.orElse(null);
    }

    @Override
    public void delete(Long id) {
        Optional<User> found = repository.findById(id);
        found.ifPresent(usuario -> repository.deleteById(id));
    }

    @Override
    public Page<User> findAll(Predicate predicate, Pageable pageable) {

        log.info("Fetching all users page");
        return repository.findAll(predicate, pageable);
    }

    @Override
    public List<User> findAll() {
        log.info("Fetching all users");
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }
}
