package br.com.acta.common.client;

import br.com.acta.common.handler.exception.BusinessRuleException;
import br.com.acta.dto.evento.EventoNotificacaoRequestDTO;
import br.com.acta.dto.evento.TipoEventoNotificacao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Component
public class FcmApiClient {
    private static final RestClient REST_CLIENT = RestClient.create();
    private final RestClient healthClient;

    private final String baseUrl;

    @Value("${acta.fcm-api.internal-token:}")
    private String internalToken;

    public FcmApiClient(@Value("${acta.fcm-api.url:}") String baseUrl) {
        this.baseUrl = baseUrl;
        this.healthClient = RestClient.builder().baseUrl(baseUrl.replaceAll("/+$", "")).build();
    }

    public void publicarReatribuicao(long idTarefa, long idEmpresa, long idDestinatario) {
        String chave = "tarefa-reatribuida-" + idTarefa + "-" + idDestinatario + "-" + UUID.randomUUID();
        publicarAposCommit(() -> publicarEventoTarefa(TipoEventoNotificacao.TAREFA_REATRIBUIDA, chave, idTarefa, idEmpresa, idDestinatario));
    }

    public boolean verificarSaude() {
        try {
            healthClient.get().uri("/api/v1/health").retrieve().toBodilessEntity();
            return true;
        } catch (RestClientException rce) { return false; }
    }

    public void publicarAtribuicao(long idTarefa, long idEmpresa, long idDestinatario) {
        String chave = "tarefa-atribuida-" + idTarefa + "-" + idDestinatario;
        publicarAposCommit(() -> publicarEventoTarefa(TipoEventoNotificacao.TAREFA_ATRIBUIDA, chave, idTarefa, idEmpresa, idDestinatario));
    }

    public void publicarStatusTarefa(TipoEventoNotificacao tipo, long idTarefa, long idEmpresa, long idDestinatario) {
        String prefixo = switch (tipo) {
            case TAREFA_BLOQUEADA -> "tarefa-bloqueada";
            case TAREFA_DESBLOQUEADA -> "tarefa-desbloqueada";
            default -> throw new BusinessRuleException("Tipo de evento de tarefa não permitido");
        };
        String chave = prefixo + "-" + idTarefa + "-" + idDestinatario + "-" + UUID.randomUUID();
        publicarAposCommit(() -> publicarEventoTarefa(tipo, chave, idTarefa, idEmpresa, idDestinatario));
    }

    public void publicarMembroAdicionadoCiclo(long idCiclo, long idEmpresa, long idDestinatario) {
        publicarAposCommit(() -> publicarEventoPorDestinatario(TipoEventoNotificacao.CICLO_MEMBRO_ADICIONADO, "CICLO", idCiclo, idEmpresa, List.of(idDestinatario), "acta://ciclos/" + idCiclo)
        );
    }

    public void publicarStatusCiclo(TipoEventoNotificacao tipo, long idCiclo, long idEmpresa, List<Long> idsDestinatarios) {
        if (!List.of(TipoEventoNotificacao.CICLO_PAUSADO, TipoEventoNotificacao.CICLO_CANCELADO, TipoEventoNotificacao.CICLO_CONCLUIDO).contains(tipo))
            throw new BusinessRuleException("Tipo de evento de ciclo não permitido");

        publicarAposCommit(() -> publicarEventoPorDestinatario(tipo, "CICLO", idCiclo, idEmpresa, idsDestinatarios, "acta://ciclos/" + idCiclo));
    }

    public void publicarTreinamentoAtribuido(long idTreinamento, long idEmpresa, long idDestinatario) {
        publicarAposCommit(() -> publicarEventoPorDestinatario(TipoEventoNotificacao.TREINAMENTO_ATRIBUIDO, "TREINAMENTO", idTreinamento, idEmpresa, List.of(idDestinatario), "acta://treinamentos/" + idTreinamento));
    }

    private void publicarAposCommit(Runnable publicacao) {
        Runnable publicacaoSegura = () -> {
            try {publicacao.run();}
            catch (BusinessRuleException ignored) {}
        };

        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            publicacaoSegura.run();
            return;
        }

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        publicacaoSegura.run();
                    }
                });
    }

    private void publicarEventoTarefa(TipoEventoNotificacao tipo, String deduplicacao, long idTarefa, long idEmpresa, long idDestinatario) {
        String id = Long.toString(idTarefa);
        enviarEvento(new EventoNotificacaoRequestDTO(deduplicacao, tipo.name(), OffsetDateTime.now(ZoneOffset.UTC), idEmpresa, List.of(idDestinatario), "TAREFA", id, "acta://tarefas/" + id, deduplicacao));
    }

    private void publicarEventoPorDestinatario(TipoEventoNotificacao tipo, String entidade, long idEntidade, long idEmpresa, List<Long> destinatarios, String deepLink) {
        OffsetDateTime ocorridoEm = OffsetDateTime.now(ZoneOffset.UTC);
        String id = Long.toString(idEntidade);

        for (Long destinatario : destinatarios) {
            String chave = tipo.name().toLowerCase() + "-" + id + "-" + destinatario + "-" + ocorridoEm.toInstant();
            enviarEvento(new EventoNotificacaoRequestDTO(chave, tipo.name(), ocorridoEm, idEmpresa, List.of(destinatario), entidade, id, deepLink, chave));
        }
    }

    private void enviarEvento(EventoNotificacaoRequestDTO evento) {
        executar(() -> REST_CLIENT.post()
                .uri(baseUrl.replaceAll("/+$", "") + "/internal/v1/eventos")
                .header("X-Internal-Token", internalToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(evento)
                .retrieve()
                .toBodilessEntity());
    }

    private void executar(Runnable requisicao) {
        try {
            requisicao.run();
        } catch (RestClientException rce) {
            throw new BusinessRuleException("Não foi possível concluir a operação na integração FCM");
        }
    }
}
