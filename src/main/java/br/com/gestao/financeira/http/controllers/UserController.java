package br.com.gestao.financeira.http.controllers;

import br.com.gestao.financeira.http.request.RoleToUserForm;
import br.com.gestao.financeira.http.request.UserRequest;
import br.com.gestao.financeira.models.Role;
import br.com.gestao.financeira.models.User;
import br.com.gestao.financeira.repositories.UserRepository;
import br.com.gestao.financeira.services.UserService;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest dto){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user").toUriString());
        return ResponseEntity.created(uri).body(service.create(dto));
    }

    @PostMapping("/role")
    public ResponseEntity<Role> createRole(@RequestBody Role dto){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/role").toUriString());
        return ResponseEntity.created(uri).body(service.saveRole(dto));
    }

    @PostMapping("/role/user")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
        service.addRoleToUser(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }


    @GetMapping("/user/{userName}")
    public ResponseEntity<User> findUserByUserName(@PathVariable("userName") String userName){
        return ResponseEntity.ok(service.getUser(userName));
    }

    @GetMapping("/user")
    public ResponseEntity<Page<User>> findAllUsers(
            @QuerydslPredicate(root = User.class, bindings = UserRepository.class) @Parameter(hidden = true) Predicate predicate,
            @Parameter(hidden = true) Pageable pageable){
        return ResponseEntity.ok(service.findAll(predicate,pageable));
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody UserRequest dto){
        return ResponseEntity.ok(service.update(id,dto));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
