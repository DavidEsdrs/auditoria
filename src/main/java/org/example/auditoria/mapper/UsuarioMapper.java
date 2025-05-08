package org.example.auditoria.mapper;

import org.example.auditoria.dto.CreateUsuarioDTO;
import org.example.auditoria.model.Departamento;
import org.example.auditoria.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mapping(source = "departamentoId", target = "departamento")
    Usuario toEntity(CreateUsuarioDTO usuario);

    default Departamento map(Long departamentoId) {
        if (departamentoId == null) {
            return null;
        }
        Departamento departamento = new Departamento();
        departamento.setId(departamentoId);
        return departamento;
    }
}
