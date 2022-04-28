package br.com.gestao.financeira.http.response;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String username;
    private Collection<String> roles = new ArrayList<>();
}
