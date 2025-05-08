package org.example.auditoria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solicitacao")
public class Solicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated
    private Tipo tipo;
    @Enumerated
    private Status status;
    private String descricao;

    @ManyToOne
    private Usuario criador;

    @ManyToOne
    private Departamento departamento;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime criacao;

    @LastModifiedDate
    private LocalDateTime ultimaEdicao;

    @OneToMany(mappedBy = "solicitacao", cascade = CascadeType.ALL)
    private List<Aprovacao> aprovacoes = new ArrayList<>();

}
