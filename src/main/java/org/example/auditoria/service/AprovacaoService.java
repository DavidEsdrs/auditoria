package org.example.auditoria.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.example.auditoria.dto.AprovarDTO;
import org.example.auditoria.model.Aprovacao;
import org.example.auditoria.model.Solicitacao;
import org.example.auditoria.model.Status;
import org.example.auditoria.repository.AprovacaoRepository;
import org.example.auditoria.repository.SolicitacaoRepository;
import org.example.auditoria.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AprovacaoService {
    @Autowired
    private AprovacaoRepository aprovacaoRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    public void doAprovacao(Long solicitacaoId, AprovarDTO dto) {
        Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoId)
            .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        UserDetailsImpl usuarioAutenticado = getUsuarioAutenticado();

        switch (usuarioAutenticado.getPapel()) {
            case GESTOR -> aprovaNoNivel(solicitacao, 1, dto.comentario());
            case FINANCEIRO -> aprovaNoNivel(solicitacao, 2, dto.comentario());
            default ->
                throw new IllegalStateException("Papel de usuário não reconhecido: " + usuarioAutenticado.getPapel());
        }

        boolean todosAprovaram = solicitacao.getAprovacoes().stream()
            .allMatch(a -> a.getStatus() == Status.APROVADA);

        if(todosAprovaram) {
            solicitacao.setStatus(Status.APROVADA);
            solicitacaoRepository.save(solicitacao);
        }
    }

    private void aprovaNoNivel(Solicitacao solicitacao, int nivel, String comentario) {
        Aprovacao aprovacao = solicitacao.getAprovacoes().stream()
            .filter(a -> a.getNivel() == nivel)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Aprovação de nível " + nivel + " não encontrada"));

        if (aprovacao.getStatus() != Status.PENDENTE) {
            throw new IllegalStateException("Aprovação já foi processada");
        }

        aprovacao.setDataAprovacao(LocalDateTime.now());
        aprovacao.setComentario(comentario);
        aprovacao.setStatus(Status.APROVADA);
        aprovacaoRepository.save(aprovacao);
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
