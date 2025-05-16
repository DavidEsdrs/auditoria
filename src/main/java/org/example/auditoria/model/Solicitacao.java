package org.example.auditoria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
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

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Enumerated(EnumType.STRING)
    private Status status;
    
    private String descricao;

    @ManyToOne
    @JsonManagedReference
    private Usuario criador;

    @ManyToOne
    private Departamento departamento;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime criacao;

    @LastModifiedDate
    private LocalDateTime ultimaEdicao;

    @OneToMany(mappedBy = "solicitacao", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Aprovacao> aprovacoes = new ArrayList<>();

}
