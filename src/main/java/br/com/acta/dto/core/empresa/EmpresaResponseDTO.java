package br.com.acta.dto.core.empresa;

import br.com.acta.dto.core.contato.email.EmailResponseDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneResponseDTO;
import br.com.acta.dto.core.empresa.endereco.EnderecoResponseDTO;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.enums.TamanhoEmpresa;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.List;

@Schema(description = "Resposta para Empresa")
public record EmpresaResponseDTO(
        Long id,
        String cnpj,
        String nome,
        TamanhoEmpresa tamanho,
        String setor,
        List<TelefoneResponseDTO> telefones,
        List<EmailResponseDTO> emails,
        List<EnderecoResponseDTO> enderecos,
        StatusGeral status,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime atualizadoEm
) {
}
