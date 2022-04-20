package br.com.gestao.financeira.http.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoLancamentoRequest {
    private Long id;
    private String nome;
}
