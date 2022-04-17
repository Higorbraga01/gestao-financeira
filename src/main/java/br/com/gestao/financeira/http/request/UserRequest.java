package br.com.gestao.financeira.http.request;

import br.com.gestao.financeira.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id;
    private String name;
    @NotBlank
    @Email
    private String username;
    private String password;
    private Collection<Role> roles = new ArrayList<>();
}
