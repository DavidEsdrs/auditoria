package org.example.auditoria.mapper;

import org.example.auditoria.dto.MinhaSolicitacaoDTO;
import org.example.auditoria.model.Solicitacao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SolicitacaoMapper {
    MinhaSolicitacaoDTO toDto(Solicitacao s);
} 