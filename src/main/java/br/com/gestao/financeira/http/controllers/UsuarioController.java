package br.com.gestao.financeira.http.controllers;

import br.com.gestao.financeira.http.request.UsuarioRequest;
import br.com.gestao.financeira.models.Usuario;
import br.com.gestao.financeira.repositories.UsuarioRepository;
import br.com.gestao.financeira.services.UsuarioService;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {

    private final UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/user")
    public ResponseEntity<Usuario> createUser(@RequestBody UsuarioRequest dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Usuario> findUserById(@PathVariable("id") Long id){
        return ResponseEntity.of(service.findById(id));
    }

    @GetMapping("/user")
    public ResponseEntity<Page<Usuario>> findAllUsers(
            @QuerydslPredicate(root = Usuario.class, bindings = UsuarioRepository.class) @Parameter(hidden = true) Predicate predicate,
            @Parameter(hidden = true) Pageable pageable){
        return ResponseEntity.ok(service.findAll(predicate,pageable));
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable("id") Long id, @RequestBody UsuarioRequest dto){
        return ResponseEntity.ok(service.update(id,dto));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
