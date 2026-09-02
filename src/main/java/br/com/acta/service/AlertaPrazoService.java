package br.com.acta.service;

import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.mapper.pdca.AlertaPrazoMapper;
import br.com.acta.dto.pdca.alerta_prazo.AlertaPrazoResponseDTO;
import br.com.acta.entity.pdca.AlertaPrazo;
import br.com.acta.entity.pdca.Tarefa;
import br.com.acta.repository.padrao.AlertaPrazoRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class AlertaPrazoService {
    private final AlertaPrazoRepository repo;
    private final AlertaPrazoMapper mapper;
    private final TarefaService tarefaService;

    private AlertaPrazo getEntity(Long idTarefa, Long idAlerta){
        if (idAlerta == null){
            Tarefa tarefa = tarefaService.getEntity(idTarefa);
            return repo.findByTarefa(tarefa).orElseThrow(() -> new ModelNotFoundException("Alerta de prazo"));
        }
        return repo.findById(idAlerta).orElseThrow(() -> new ModelNotFoundException("Alerta de prazo"));
    }

    @Transactional(readOnly = true)
    public AlertaPrazoResponseDTO buscar(Long idTarefa){
        AlertaPrazo alertaPrazo = getEntity(idTarefa, null);
        return mapper.toResponse(alertaPrazo);
    }

    @Scheduled(cron = "0 0 8 * * *", zone = "America/Sao_Paulo")
    @Transactional
    public void inserir() {
        repo.gerarAlertasAtraso();
    }

    @Transactional
    public AlertaPrazoResponseDTO marcarLido(Long idTarefa, Long idAlerta, Long idUsuario){
        AlertaPrazo alertaPrazo = getEntity(idTarefa, idAlerta);

        Validador.validarMesmoId(idUsuario, alertaPrazo.getUsuarioDestino().getId(), true);

        alertaPrazo.setLidoEm(OffsetDateTime.now());
        AlertaPrazo salvo = repo.save(alertaPrazo);
        return mapper.toResponse(salvo);
    }
}
