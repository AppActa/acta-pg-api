package br.com.acta.dto.core.empresa;


import br.com.acta.common.utils.Formatador;
import br.com.acta.dto.core.empresa.endereco.EnderecoMapper;
import br.com.acta.entity.core.Empresa;
import br.com.acta.dto.mapper.base.AuditoriaBaseMapper;
import br.com.acta.dto.core.contato.email.EmailEmpresaMapper;
import br.com.acta.dto.core.contato.telefone.TelefoneEmpresaMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {Formatador.class, EnderecoMapper.class, EmailEmpresaMapper.class, TelefoneEmpresaMapper.class})
public interface EmpresaMapper
extends AuditoriaBaseMapper<EmpresaRequestDTO, EmpresaResponseDTO, Empresa> {
    @Mapping(source = "cnpj", target = "cnpj", qualifiedByName = "formatarCnpj")
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
