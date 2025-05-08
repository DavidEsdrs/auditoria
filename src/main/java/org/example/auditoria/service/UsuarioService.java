package org.example.auditoria.service;

import org.example.auditoria.dto.CreateUsuarioDTO;
import org.example.auditoria.exception.EmailUtilizadoException;
import org.example.auditoria.exception.UsuarioNaoEncontradoException;
import org.example.auditoria.mapper.UsuarioMapper;
import org.example.auditoria.model.Usuario;
import org.example.auditoria.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public Usuario createUsuario(CreateUsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        Optional<Usuario> usuarioInDb = usuarioRepository.findByEmail(usuarioDTO.email());
        if(usuarioInDb.isPresent()) {
            throw new EmailUtilizadoException(usuarioDTO.email());
        }
        usuarioRepository.save(usuario);
        return usuario;
    }

    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
