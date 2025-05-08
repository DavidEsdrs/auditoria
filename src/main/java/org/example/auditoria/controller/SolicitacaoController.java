package org.example.auditoria.controller;

import java.util.List;

import org.example.auditoria.dto.CreateSolicitacaoDTO;
import org.example.auditoria.model.Solicitacao;
import org.example.auditoria.service.SolicitacaoService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
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
    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }
    
    @PostMapping
    public ResponseEntity<Solicitacao> postMethodName(CreateSolicitacaoDTO dto) {
        return ResponseEntity.ok(solicitacaoService.createSolicitacao(dto));
    }
    
    @GetMapping
    public ResponseEntity<List<Solicitacao>> getSolicitacoes() {
        return ResponseEntity.ok(solicitacaoService.getSolicitacoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitacao> getSolicitacaoById(@PathVariable Long id) {
        return ResponseEntity.ok(solicitacaoService.getSolicitacaoById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteSolicitacaoById(@PathVariable Long id) {
        solicitacaoService.deleteSolicitacaoById(id);
    }
}
