package br.com.acta.mapper.core;


import br.com.acta.dto.core.empresa.EmpresaRequestDTO;
import br.com.acta.dto.core.empresa.EmpresaResponseDTO;
import br.com.acta.entity.core.Empresa;
import br.com.acta.mapper.base.AuditoriaBaseMapper;
import br.com.acta.mapper.core.contato.EmailEmpresaMapper;
import br.com.acta.mapper.core.contato.TelefoneEmpresaMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class, EmailEmpresaMapper.class, TelefoneEmpresaMapper.class})
public interface EmpresaMapper
extends AuditoriaBaseMapper<EmpresaRequestDTO, EmpresaResponseDTO, Empresa> {
    EmpresaResponseDTO toResponse(Empresa empresa);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "colaboradores", ignore = true)
    @Override
    Empresa toEntity(EmpresaRequestDTO dto);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "colaboradores", ignore = true)
    @Override
    void updateEntity(EmpresaRequestDTO dto, @MappingTarget Empresa empresa);

    @AfterMapping
    default void link(@MappingTarget Empresa empresa){
        empresa.getEmails().forEach(e -> e.setEmpresa(empresa));
        empresa.getTelefones().forEach(t -> t.setEmpresa(empresa));
        empresa.getEnderecos().forEach(e -> e.setEmpresa(empresa));
    }
}
