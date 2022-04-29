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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserService implements BaseService<User, UserRequest>, UserDetailsService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new  org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Autowired
    public UserService(UserRepository repository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(UserRequest dto) {
        log.info("Saving new user {} to the database", dto.getName());
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        User saved = repository.saveAndFlush(user);
        this.addRoleToUser(saved.getUsername(),"ROLE_USER");
        return saved;
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

    public static User authenticated() {
        try {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            return null;
        }
    }
}
