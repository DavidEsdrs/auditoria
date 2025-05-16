package org.example.auditoria.service;

import jakarta.transaction.Transactional;
import org.example.auditoria.dto.CreateSolicitacaoDTO;
import org.example.auditoria.dto.MinhaSolicitacaoDTO;
import org.example.auditoria.exception.SolicitacaoNaoEncontradaException;
import org.example.auditoria.mapper.CreateSolicitacaoMapper;
import org.example.auditoria.mapper.SolicitacaoMapper;
import org.example.auditoria.model.*;
import org.example.auditoria.repository.AprovacaoRepository;
import org.example.auditoria.repository.SolicitacaoRepository;
import org.example.auditoria.repository.UsuarioRepository;
import org.example.auditoria.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolicitacaoService {
    @Autowired
    SolicitacaoRepository solicitacaoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;
    
    @Autowired
    AprovacaoRepository aprovacaoRepository;

    @Autowired
    CreateSolicitacaoMapper createSolicitacaoMapper;

    @Autowired
    SolicitacaoMapper solicitacaoMapper;

    @Transactional
    public Solicitacao createSolicitacao(CreateSolicitacaoDTO dto) {
        UserDetailsImpl usuarioAutenticado = getUsuarioAutenticado();

        Departamento departamento = usuarioAutenticado.getDepartamento();
        Usuario criador = usuarioRepository.findById(usuarioAutenticado.getId())
                .orElseThrow(() -> new RuntimeException("Criador não encontrado"));
        Usuario gestor = usuarioRepository.findById(dto.gestorId())
                .orElseThrow(() -> new RuntimeException("Aprovador Gestor não encontrado"));
        Usuario financeiro = usuarioRepository.findById(dto.financeiroId())
                .orElseThrow(() -> new RuntimeException("Aprovador Financeiro não encontrado"));

        boolean mesmoDepartamento = criador.getDepartamento().equals(criador.getDepartamento());

        if(!mesmoDepartamento) {
            throw new RuntimeException("O Gestor indicado precisa ser do mesmo departamento que o solicitanto");
        }

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

    public List<Solicitacao> getSolicitacoesParaMim() {
        var usuarioAutenticado = getUsuarioAutenticado();
        List<Aprovacao> aprovacoes = aprovacaoRepository.findByAprovadorId(usuarioAutenticado.getId());
        List<Long> aprovacoesIds = aprovacoes.stream()
            .map(a -> a.getId())
            .collect(Collectors.toList());
        List<Solicitacao> solicitacoes = solicitacaoRepository.findByIdIn(aprovacoesIds);
        return solicitacoes;
    }

    public void deleteSolicitacaoById(Long id) {
        Optional<Solicitacao> solicitacaoOpt = solicitacaoRepository.findById(id);
        if (solicitacaoOpt.isEmpty()) {
            throw new SolicitacaoNaoEncontradaException(id);
        }
        boolean isAdmin = getRoleUsuarioAutenticado().equals("ADMIN");
        if (!isAdmin) {
            throw new RuntimeException("Não autorizado");
        }
        
        Solicitacao solicitacao = solicitacaoOpt.get();
        UserDetailsImpl usuarioAutenticado = getUsuarioAutenticado();

        if(!solicitacao.getCriador().getEmail().equals(usuarioAutenticado.getUsername())) {
            throw new RuntimeException("Não autorizado - Usuário não é o criador da solicitação");
        }

        solicitacaoRepository.deleteById(id);
    }

    public List<MinhaSolicitacaoDTO> getMinhasSolicitcoes() {
        UserDetailsImpl usuarioAutenticado = getUsuarioAutenticado();
        List<Solicitacao> minhasSolicitcoes = solicitacaoRepository.findByCriadorId(usuarioAutenticado.getId());
        return minhasSolicitcoes.stream()
            .map(s -> solicitacaoMapper.toDto(s))
            .toList();
    }

    public String getRoleUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(null);
        }

        return null;
    }

    public UserDetailsImpl getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl usuarioDetails = (UserDetailsImpl) authentication.getPrincipal();
            return usuarioDetails;
        }

        throw new IllegalStateException("Usuário não autenticado");
    }
}
