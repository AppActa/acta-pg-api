package br.com.acta.service;

import br.com.acta.dto.pdca.alerta_prazo.AlertaPrazoResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.pdca.AlertaPrazo;
import br.com.acta.entity.pdca.Tarefa;
import br.com.acta.common.handler.exception.BusinessRuleException;
import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.dto.mapper.pdca.AlertaPrazoMapper;
import br.com.acta.repository.padrao.AlertaPrazoRepository;
import br.com.acta.common.utils.Validador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class AlertaPrazoService {
    private final AlertaPrazoRepository repo;
    private final AlertaPrazoMapper mapper;
    private final TarefaService tarefaService;
    private final UsuarioService usuarioService;

    private AlertaPrazo getEntity(Long idTarefa, Long idAlerta){
        if (idAlerta == null){
            Tarefa tarefa = tarefaService.getEntity(idTarefa);
            return repo.findByTarefa(tarefa).orElseThrow(() -> new ModelNotFoundException("Alerta de prazo"));
        }
        return repo.findById(idAlerta).orElseThrow(() -> new ModelNotFoundException("Alerta de prazo"));
    }

    public AlertaPrazoResponseDTO buscar(Long idTarefa){
        AlertaPrazo alertaPrazo = getEntity(idTarefa, null);
        return mapper.toResponse(alertaPrazo);
    }

    public AlertaPrazoResponseDTO inserir(Long idTarefa) {
        Tarefa tarefa = tarefaService.getEntity(idTarefa);
        Usuario usuarioDestino = usuarioService.getEntity(tarefa.getResponsavel().getId());
        Validador.validarCicloAberto(tarefa.getPlanoAcao().getCiclo());

        String mensagem = gerarAlerta(tarefa, usuarioDestino);


        AlertaPrazo alertaPrazo = new AlertaPrazo();
        alertaPrazo.setTarefa(tarefa);
        alertaPrazo.setUsuarioDestino(usuarioDestino);
        alertaPrazo.setMensagem(mensagem);

        AlertaPrazo salvo = repo.save(alertaPrazo);
        return mapper.toResponse(salvo);
    }

    public AlertaPrazoResponseDTO marcarLido(Long idTarefa, Long idAlerta, Long idUsuario){
        AlertaPrazo alertaPrazo = getEntity(idTarefa, idAlerta);

        Validador.validarMesmoId(idUsuario, alertaPrazo.getUsuarioDestino().getId(), true);

        alertaPrazo.setLidoEm(OffsetDateTime.now());
        AlertaPrazo salvo = repo.save(alertaPrazo);
        return mapper.toResponse(salvo);
    }

    private String gerarAlerta(Tarefa tarefa, Usuario usuario){
        long diasAtrasado = ChronoUnit.DAYS.between(tarefa.getDataFimPrevista(), LocalDate.now());

        if (diasAtrasado > 0) return usuario.getNome() + ", a tarefa " + tarefa.getTitulo() + " está atrasada em " + diasAtrasado + " dias";
        else throw new BusinessRuleException("A tarefa não está atrasada");
    }
}
