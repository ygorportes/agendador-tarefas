package com.ygorportes.agendadortarefas.business;

import com.ygorportes.agendadortarefas.business.dto.TarefasDTO;
import com.ygorportes.agendadortarefas.business.mapper.TarefasConverter;
import com.ygorportes.agendadortarefas.business.mapper.TarefasUpdateConverter;
import com.ygorportes.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.ygorportes.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.ygorportes.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.ygorportes.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.ygorportes.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;
    private final TarefasUpdateConverter tarefasUpdateConverter;

    public TarefasDTO gravarTarefa(String token, TarefasDTO tarefasDTO) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        tarefasDTO.setDataCriacao(LocalDateTime.now());
        tarefasDTO.setStatus(StatusNotificacaoEnum.PENDENTE);
        tarefasDTO.setEmailUsuario(email);
        TarefasEntity entity = tarefasConverter.paraTarefaEntity(tarefasDTO);

        return tarefasConverter.paraTarefaDTO(
                tarefasRepository.save(entity));
    }

    public List<TarefasDTO> buscaTarefaAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {

        return tarefasConverter.paraListaTarefasDTO(
                tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal)
        );
    }

    public List<TarefasDTO> buscaTerafasPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        return tarefasConverter.paraListaTarefasDTO(
                tarefasRepository.findByEmailUsuario(email)
        );
    }

    public void deletaTarefaPorId(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa, id inexistente " + id,
                    e.getCause());
        }
    }

    public TarefasDTO alteraStatus(StatusNotificacaoEnum status, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Tarefa não encontrada " + id));
            entity.setStatus(status);

            return tarefasConverter.paraTarefaDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getCause());
        }
    }

    public TarefasDTO updateTarefas(TarefasDTO tarefasDTO, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Tarefa não encontrada " + id));
            tarefasUpdateConverter.updateDeTarefas(tarefasDTO, entity);
            return tarefasConverter.paraTarefaDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getCause());
        }
    }
}
