package br.com.gestao.financeira.http.controllers;

import br.com.gestao.financeira.JwtUtil;
import br.com.gestao.financeira.http.request.RoleToUserForm;
import br.com.gestao.financeira.http.request.UserRequest;
import br.com.gestao.financeira.models.Role;
import br.com.gestao.financeira.models.User;
import br.com.gestao.financeira.repositories.UserRepository;
import br.com.gestao.financeira.services.UserService;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService service;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
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

    @PostMapping("/user/role")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
        service.addRoleToUser(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){
            try {
                String token_request = jwtUtil.getTokenFromAuthorizationHeader(authorizationHeader);
                DecodedJWT decodedJWT = jwtUtil.verifyToken(token_request);
                String username = decodedJWT.getSubject();
                UserDetails userDetails = service.loadUserByUsername(username);
                String acess_token = jwtUtil.generateAcessToken((org.springframework.security.core.userdetails.User) userDetails, request);
                String refresh_token = jwtUtil.generateRefreshToken((org.springframework.security.core.userdetails.User) userDetails, request);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("acess_token", acess_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);
            }catch (Exception exception){
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
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
