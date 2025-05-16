package org.example.auditoria.controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.example.auditoria.dto.AprovarDTO;
import org.example.auditoria.dto.CreateSolicitacaoDTO;
import org.example.auditoria.dto.MinhaSolicitacaoDTO;
import org.example.auditoria.model.Solicitacao;
import org.example.auditoria.service.AprovacaoService;
import org.example.auditoria.service.SolicitacaoService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/solicitacao")
public class SolicitacaoController {
    @Autowired
    private SolicitacaoService solicitacaoService;

    @Autowired
    private AprovacaoService aprovacaoService;

    @PostMapping
    public ResponseEntity<Void> createSolicitacao(@RequestBody CreateSolicitacaoDTO dto) {
        solicitacaoService.createSolicitacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyRole('GESTOR', 'FINANCEIRO')")
    @PostMapping("/{solicitacaoId}/aprovar")
    public void aprovarSolicitacao(@PathVariable Long solicitacaoId, @RequestBody Optional<AprovarDTO> dto) {
        aprovacaoService.doAprovacao(solicitacaoId, dto.orElse(new AprovarDTO("")));
    }
    
    
    @GetMapping
    public ResponseEntity<List<Solicitacao>> getSolicitacoes() {
        return ResponseEntity.ok(solicitacaoService.getSolicitacoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitacao> getSolicitacaoById(@PathVariable Long id) {
        return ResponseEntity.ok(solicitacaoService.getSolicitacaoById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<List<MinhaSolicitacaoDTO>> getMinhasSolicitcaoes() {
        return ResponseEntity.ok(solicitacaoService.getMinhasSolicitcoes());
    }

    @PreAuthorize("hasAnyRole('GESTOR', 'FINANCEIRO')")
    @GetMapping("/forme")
    public ResponseEntity<List<Solicitacao>> getSolicitacoesPraMin() {
        return ResponseEntity.ok(solicitacaoService.getSolicitacoesParaMim());
    }

    @DeleteMapping("/{id}")
    public void deleteSolicitacaoById(@PathVariable Long id) {
        solicitacaoService.deleteSolicitacaoById(id);
    }
}
