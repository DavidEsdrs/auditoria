package org.example.auditoria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100)
    private String nome;

    @Column(unique = true, nullable = false, length = 30)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    private Papel papel;

    @ManyToOne(fetch = FetchType.EAGER)
    private Departamento departamento;

    @OneToMany(mappedBy = "criador")
    private List<Solicitacao> solicitacoes;

}
