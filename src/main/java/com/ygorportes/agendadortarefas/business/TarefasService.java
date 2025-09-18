package com.ygorportes.agendadortarefas.business;

import com.ygorportes.agendadortarefas.business.dto.TarefasDTO;
import com.ygorportes.agendadortarefas.business.mapper.TarefasConverter;
import com.ygorportes.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.ygorportes.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.ygorportes.agendadortarefas.infrastructure.repository.TareasRepository;
import com.ygorportes.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TareasRepository tareasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token, TarefasDTO tarefasDTO) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        tarefasDTO.setDataCriacao(LocalDateTime.now());
        tarefasDTO.setStatus(StatusNotificacaoEnum.PENDENTE);
        tarefasDTO.setEmailUsuario(email);
        TarefasEntity entity = tarefasConverter.paraTarefaEntity(tarefasDTO);

        return tarefasConverter.paraTarefaDTO(
                tareasRepository.save(entity));
    }

    public List<TarefasDTO> buscaTarefaAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {

        return tarefasConverter.paraListaTarefasDTO(
                tareasRepository.findByDataEventoBetween(dataInicial, dataFinal)
        );
    }

    public List<TarefasDTO> buscaTerafasPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        return tarefasConverter.paraListaTarefasDTO(
                tareasRepository.findByEmailUsuario(email)
        );
    }
}
