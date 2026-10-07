package br.com.acta.dto.core.empresa;

import br.com.acta.entity.core.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OnboardingMapper {
    @Mapping(source = "empresa.id", target = "idEmpresa")
    @Mapping(source = "empresa.status", target = "statusEmpresa")
    @Mapping(source = "id", target = "idUsuario")
    @Mapping(source = "tipo", target = "tipoUsuario")
    @Mapping(source = "status", target = "statusUsuario")
    OnboardingDadosDTO toDados(Usuario usuario);

    default OnboardingResponseDTO toResponse(Usuario usuario) {
        return toResponse(usuario, false, false, false);
    }

    default OnboardingResponseDTO toResponse(Usuario usuario, boolean empresaExistente, boolean conviteEnviado, boolean proximaEtapa) {
        OnboardingDadosDTO dados = toDados(usuario);
        return new OnboardingResponseDTO(
                empresaExistente,
                conviteEnviado,
                proximaEtapa,
                dados.idEmpresa(),
                dados.statusEmpresa(),
                dados.idUsuario(),
                dados.tipoUsuario(),
                dados.statusUsuario(),
                null);
    }

    default OnboardingResponseDTO solicitarDadosEmpresa() {
        return new OnboardingResponseDTO(false, false, true, null, null, null, null, null, null);
    }
}
