package org.example.auditoria.service;

import org.example.auditoria.dto.CreateDepartamentoDTO;
import org.example.auditoria.exception.DepartamentoNaoEncontradoException;
import org.example.auditoria.model.Departamento;
import org.example.auditoria.repository.DepartamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService {
    private final DepartamentoRepository departamentoRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    public Departamento createDepartamento(CreateDepartamentoDTO departamentoDTO) {
        Departamento departamento = new Departamento();
        departamento.setNome(departamentoDTO.nome());
        departamentoRepository.save(departamento);
        return departamento;
    }

    public Departamento getDepartamentoById(Long id) {
        return departamentoRepository.findById(id).orElseThrow(() -> new DepartamentoNaoEncontradoException(id));
    }

    public List<Departamento> getDepartamentos() {
        return departamentoRepository.findAll();
    }

    public void deleteDepartamento(Long id) {
        departamentoRepository.deleteById(id);
    }
}
