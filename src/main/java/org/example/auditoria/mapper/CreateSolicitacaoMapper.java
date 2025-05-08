package org.example.auditoria.mapper;

import org.example.auditoria.dto.CreateSolicitacaoDTO;
import org.example.auditoria.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateSolicitacaoMapper {
    public Solicitacao toEntity(CreateSolicitacaoDTO dto,
                                Usuario criador,
                                Departamento departamento,
                                Usuario gestor,
                                Usuario financeiro) {
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setTipo(dto.tipo());
        solicitacao.setStatus(Status.PENDENTE);
        solicitacao.setDescricao(dto.descricao());
        solicitacao.setCriador(criador);
        solicitacao.setDepartamento(departamento);

        Aprovacao aprovacaoGestor = new Aprovacao();
        aprovacaoGestor.setNivel(1);
        aprovacaoGestor.setStatus(Status.PENDENTE);
        aprovacaoGestor.setAprovador(gestor);
        aprovacaoGestor.setSolicitacao(solicitacao);

        Aprovacao aprovacaoFinanceiro = new Aprovacao();
        aprovacaoFinanceiro.setNivel(2);
        aprovacaoFinanceiro.setStatus(Status.PENDENTE);
        aprovacaoFinanceiro.setAprovador(financeiro);
        aprovacaoFinanceiro.setSolicitacao(solicitacao);

        solicitacao.setAprovacoes(new ArrayList<>(List.of(
                aprovacaoGestor, aprovacaoFinanceiro
        )));

        return solicitacao;
    }
}
