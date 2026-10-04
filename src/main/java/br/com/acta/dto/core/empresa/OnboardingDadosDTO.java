package br.com.acta.dto.core.empresa;

import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.enums.TipoUsuario;

record OnboardingDadosDTO(
        Long idEmpresa,
        StatusGeral statusEmpresa,
        Long idUsuario,
        TipoUsuario tipoUsuario,
        StatusGeral statusUsuario
) {
}
