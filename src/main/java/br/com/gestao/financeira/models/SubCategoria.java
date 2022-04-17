package br.com.gestao.financeira.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SubCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoria_id", foreignKey = @ForeignKey(name = "CATEGORIA_FK"), referencedColumnName = "id")
    @JsonBackReference
    private Categoria categoria;
}
