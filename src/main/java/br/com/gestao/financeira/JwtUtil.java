package br.com.gestao.financeira;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateAcessToken(User user, HttpServletRequest request) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC256(secret.getBytes()));
    }

    public String generateRefreshToken(User user, HttpServletRequest request) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .withIssuer(request.getRequestURL().toString())
                .sign(Algorithm.HMAC256(secret.getBytes()));
    }

    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String authorizationHeader) {
        String token = this.getTokenFromAuthorizationHeader(authorizationHeader);
        DecodedJWT decodedJWT = this.verifyToken(token);
        String userName = this.getUserNameFromToken(decodedJWT.getToken());
        Collection<SimpleGrantedAuthority> authorities = this.getRolesFromSubect(decodedJWT.getToken());
        return  new UsernamePasswordAuthenticationToken(userName,null, authorities);
    }

    public String getUserNameFromToken(String token){
        return verifyToken(token).getSubject();
    }

    public Collection<SimpleGrantedAuthority> getRolesFromSubect(String subject) {
        String[] roles = verifyToken(subject).getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        return authorities;
    }

    public DecodedJWT verifyToken(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(this.secret.getBytes())).build();
       return verifier.verify(token);
    }

    public String getTokenFromAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }
}
