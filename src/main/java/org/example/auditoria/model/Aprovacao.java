package org.example.auditoria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aprovacao")
public class Aprovacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int nivel;

    @Enumerated
    private Status status;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dataAprovacao;

    private String comentario;

    @ManyToOne
    private Solicitacao solicitacao;

    @ManyToOne
    private Usuario aprovador;

}
