package br.com.gestao.financeira.http.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoriaRequest {

    private Long id;
    @NotBlank
    @NotNull
    private String nome;
    @NotNull
    private Long categoriaId;
}
