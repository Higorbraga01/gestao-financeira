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
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @ManyToOne
    private Categoria categoria;
    @ManyToOne
    private Periodicidade periodicidade;
    @ManyToOne
    private User user;
    private Integer quantidadeRepeticao;
    private BigInteger valorTotal;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAlteracao;

    @ManyToOne
    private TipoLancamento tipoLancamento;
}
