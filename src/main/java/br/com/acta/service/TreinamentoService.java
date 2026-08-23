package br.com.acta.service;

import br.com.acta.common.handler.exception.ActiveEntityDeletionException;
import br.com.acta.common.handler.exception.InvalidRequestException;
import br.com.acta.common.utils.ConversorObject;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.mapper.pdca.TreinamentoMapper;
import br.com.acta.dto.pdca.treinamento.TreinamentoRequestDTO;
import br.com.acta.dto.pdca.treinamento.TreinamentoResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.StatusTreinamento;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.entity.pdca.Treinamento;
import br.com.acta.repository.padrao.TreinamentoRepository;
import br.com.acta.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TreinamentoService
extends BaseService<TreinamentoRequestDTO, TreinamentoResponseDTO, Treinamento> {
    private final TreinamentoRepository repo;
    private final TreinamentoMapper mapper;
    private final CicloService cicloService;
    private final UsuarioService usuarioService;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("titulo", "descricao", "dataTreinamento", "obrigatorio", "idAnexoMongo", "idCiclo", "idResponsavel"),
            Set.of("titulo", "descricao", "dataTreinamento", "obrigatorio")
    );

    public TreinamentoService(TreinamentoRepository repo, TreinamentoMapper mapper, CicloService cicloService, UsuarioService usuarioService){
        super(repo, mapper, Treinamento.class);
        this.repo = repo;
        this.mapper = mapper;
        this.cicloService = cicloService;
        this.usuarioService = usuarioService;
    }

    @Transactional
    @Override
    public TreinamentoResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        Treinamento treinamento = getEntity(id);

        if (campos.containsKey("titulo")) treinamento.setTitulo((String) campos.get("titulo"));
        if (campos.containsKey("descricao")) treinamento.setDescricao((String) campos.get("descricao"));
        if (campos.containsKey("dataTreinamento")) {
            Object dataTreinamentoObject = campos.get("dataTreinamento");
            LocalDate dataTreinamento = ConversorObject.toLocalDate(dataTreinamentoObject, false);

            if (dataTreinamento.isBefore(LocalDate.now()))
                throw new InvalidRequestException("A data do treinamento não pode ser anterior à data atual");

            treinamento.setDataTreinamento(dataTreinamento);
        }
        if (campos.containsKey("obrigatorio")) treinamento.setObrigatorio((Boolean) campos.get("obrigatorio"));

        Treinamento salvo = repo.save(treinamento);
        return mapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public List<TreinamentoResponseDTO> buscarTreinamentos(Long idCiclo){
        Ciclo ciclo = cicloService.getEntity(idCiclo);
        List<Treinamento> treinamentos = repo.findByCiclo(ciclo);

        return mapper.toResponseList(treinamentos);
    }

    @Transactional
    public TreinamentoResponseDTO inserir(Long idCiclo, TreinamentoRequestDTO dto) {
        Ciclo ciclo = cicloService.getEntity(idCiclo);
        Usuario usuario = usuarioService.getEntity(dto.idResponsavel());
        Validador.validarMesmoCiclo(ciclo, usuario.getCiclos());

        Treinamento treinamento = mapper.toEntity(dto);

        treinamento.setCiclo(ciclo);
        treinamento.setResponsavel(usuario);

        Treinamento salvo = repo.save(treinamento);
        return mapper.toResponse(salvo);
    }

    @Transactional
    @Override
    public void excluir(Long id) {
        Treinamento treinamento = getEntity(id);
        Validador.validarCicloAberto(treinamento.getCiclo());

        if (treinamento.getDataTreinamento().isBefore(LocalDate.now())) {
            throw new InvalidRequestException("Não é possível excluir treinamentos que já foram terminados");
        }

        treinamento.getParticipantes().forEach(participante -> {
            if (participante.getStatus() == StatusTreinamento.CONCLUIDO){
                throw new ActiveEntityDeletionException("Treinamento");
            }
        });

        repo.delete(treinamento);
    }

}
