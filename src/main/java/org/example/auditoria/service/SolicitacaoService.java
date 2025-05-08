package org.example.auditoria.service;

import jakarta.transaction.Transactional;
import org.example.auditoria.dto.CreateSolicitacaoDTO;
import org.example.auditoria.exception.SolicitacaoNaoEncontradaException;
import org.example.auditoria.mapper.CreateSolicitacaoMapper;
import org.example.auditoria.model.*;
import org.example.auditoria.repository.DepartamentoRepository;
import org.example.auditoria.repository.SolicitacaoRepository;
import org.example.auditoria.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitacaoService {
    private final SolicitacaoRepository solicitacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final DepartamentoRepository departamentoRepository;
    private final CreateSolicitacaoMapper createSolicitacaoMapper;

    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository, UsuarioRepository usuarioRepository, DepartamentoRepository departamentoRepository, CreateSolicitacaoMapper createSolicitacaoMapper) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.departamentoRepository = departamentoRepository;
        this.createSolicitacaoMapper = createSolicitacaoMapper;
    }

    @Transactional
    public Solicitacao createSolicitacao(CreateSolicitacaoDTO dto) {
        Departamento departamento = departamentoRepository.findById(dto.departamentoId())
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));
        Usuario criador = usuarioRepository.findById(dto.criadorId())
                .orElseThrow(() -> new RuntimeException("Criador não encontrado"));
        Usuario gestor = usuarioRepository.findById(dto.gestorId())
                .orElseThrow(() -> new RuntimeException("Gestor não encontrado"));
        Usuario financeiro = usuarioRepository.findById(dto.financeiroId())
                .orElseThrow(() -> new RuntimeException("Financeiro não encontrado"));
        Solicitacao solicitacao = createSolicitacaoMapper.toEntity(dto, criador, departamento, gestor, financeiro);
        solicitacaoRepository.save(solicitacao);
        return solicitacao;
    }

    public Solicitacao getSolicitacaoById(Long id) {
        return solicitacaoRepository.findById(id).orElseThrow(() -> new SolicitacaoNaoEncontradaException(id));
    }

    public List<Solicitacao> getSolicitacoes() {
        return solicitacaoRepository.findAll();
    }

    public void deleteSolicitacaoById(Long id) {
        solicitacaoRepository.deleteById(id);
    }
}
