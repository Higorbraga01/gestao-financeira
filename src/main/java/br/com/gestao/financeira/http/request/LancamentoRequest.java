package br.com.gestao.financeira.http.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoRequest {

    private Long id;
    private String nome;
    private Long categoriaId;
    private Long periodicidadeId;
    private Long userId;
    private Integer quantidadeRepeticao;
    private BigInteger valorTotal;
    @NotNull(message = "A data de criacao não pode ser nula")
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAlteracao;
    private Long tipoLancamentoId;
}
