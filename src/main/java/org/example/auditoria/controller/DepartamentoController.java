package org.example.auditoria.controller;

import org.example.auditoria.dto.CreateDepartamentoDTO;
import org.example.auditoria.model.Departamento;
import org.example.auditoria.repository.DepartamentoRepository;
import org.example.auditoria.service.DepartamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamento")
public class DepartamentoController {
    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @PostMapping
    public ResponseEntity<Departamento> createDepartamento(CreateDepartamentoDTO departamentoDTO) {
        return ResponseEntity.ok(departamentoService.createDepartamento(departamentoDTO));
    }

    @GetMapping
    public ResponseEntity<List<Departamento>> getDepartamentos() {
        return ResponseEntity.ok(departamentoService.getDepartamentos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departamento> getDepartamentoById(@PathVariable Long id) {
        return ResponseEntity.ok(departamentoService.getDepartamentoById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteDepartamentoById(@PathVariable Long id) {
        departamentoService.deleteDepartamento(id);
    }
}
