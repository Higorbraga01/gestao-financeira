package br.com.gestao.financeira.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParcelaLancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Lancamento lancamento;
    private LocalDateTime data;
    private BigInteger valor;
    private String status;
    private Integer numero;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAlteracao;
}
