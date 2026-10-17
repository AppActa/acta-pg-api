package br.com.acta.dto.evento;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.OffsetDateTime;
import java.util.List;

public record EventoNotificacaoRequestDTO(
        @JsonAlias("id_evento") String idEvento,
        @JsonAlias("tipo_evento") String tipoEvento,
        @JsonAlias("ocorrido_em") OffsetDateTime ocorridoEm,
        @JsonAlias("id_empresa") Long idEmpresa,
        @JsonAlias("ids_destinatarios") List<Long> idsDestinatarios,
        @JsonAlias("tipo_entidade") String tipoEntidade,
        @JsonAlias("id_entidade") String idEntidade,
        @JsonAlias("deep_link") String deepLink,
        @JsonAlias("chave_deduplicacao") String chaveDeduplicacao
) {}