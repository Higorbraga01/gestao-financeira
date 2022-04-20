package br.com.gestao.financeira.http.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParcelaLancamentoRequest {
    
    private Long id;
    private Long lancamentoId;
    private LocalDateTime data;
    private BigInteger valor;
    private String status;
    private Integer numero;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAlteracao;
    
}
